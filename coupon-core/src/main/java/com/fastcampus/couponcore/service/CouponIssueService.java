package com.fastcampus.couponcore.service;

import static com.fastcampus.couponcore.exception.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.couponcore.exception.CouponIssueException;
import com.fastcampus.couponcore.model.Coupon;
import com.fastcampus.couponcore.model.CouponIssue;
import com.fastcampus.couponcore.repository.mysql.CouponIssueJpaRepository;
import com.fastcampus.couponcore.repository.mysql.CouponIssueRepository;
import com.fastcampus.couponcore.repository.mysql.CouponJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CouponIssueService {

	private final CouponJpaRepository couponJpaRepository;
	private final CouponIssueJpaRepository couponIssueJpaRepository;
	private final CouponIssueRepository couponIssueRepository;

	@Transactional
	public void issue(long couponId, long userId) {
		Coupon coupon = findCouponWithLock(couponId);
		coupon.issue();
		saveCouponIssue(couponId, userId);
		//publishCouponEvent(coupon);
	}

	@Transactional(readOnly = true)
	public Coupon findCoupon(long couponId) {
		return couponJpaRepository.findById(couponId).orElseThrow(() -> {
			throw new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
		});
	}

	@Transactional
	public Coupon findCouponWithLock(long couponId) {
		return couponJpaRepository.findCouponWithLock(couponId).orElseThrow(() -> {
			throw new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
		});
	}

	@Transactional
	public CouponIssue saveCouponIssue(long couponId, long userId) {
		checkAlreadyIssuance(couponId, userId);
		CouponIssue couponIssue = CouponIssue.builder()
			.couponId(couponId)
			.userId(userId)
			.build();
		return couponIssueJpaRepository.save(couponIssue);
	}

	private void checkAlreadyIssuance(long couponId, long userId) {
		CouponIssue issue = couponIssueRepository.findFirstCouponIssue(couponId, userId);
		if (issue != null) {
			throw new CouponIssueException(DUPLICATED_COUPON_ISSUE, "이미 발급된 쿠폰입니다. user_id: %d, coupon_id: %d".formatted(userId, couponId));
		}
	}

	// private void publishCouponEvent(Coupon coupon) {
	// 	if (coupon.isIssueComplete()) {
	// 		applicationEventPublisher.publishEvent(new CouponIssueCompleteEvent(coupon.getId()));
	// 	}
	// }


}
