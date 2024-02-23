package com.fastcampus.couponcore.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastcampus.couponcore.model.CouponIssue;

public interface CouponIssueJpaRepository extends JpaRepository<CouponIssue,Long> {
}
