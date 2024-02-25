package com.fastcampus.couponapi;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fastcampus.couponapi.controller.dto.CouponIssueResponseDto;
import com.fastcampus.couponcore.exception.CouponIssueException;

@RestControllerAdvice
public class CouponControllerAdvice {

	@ExceptionHandler(CouponIssueException.class)
	public CouponIssueResponseDto couponIssueExceptionHandler(CouponIssueException exception) {
		return new CouponIssueResponseDto(false, exception.getErrorCode().message);
	}
}