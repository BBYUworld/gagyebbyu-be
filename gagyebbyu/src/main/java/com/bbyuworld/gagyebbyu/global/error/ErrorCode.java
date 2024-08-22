package com.bbyuworld.gagyebbyu.global.error;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	/**
	 * default
	 */
	SYSTEM_ERROR(INTERNAL_SERVER_ERROR, "SYSTEM-000", "서비스에 장애가 발생했습니다."),
	BAD_REQUEST_ERROR(BAD_REQUEST, "SYSTEM-001", "유효하지 않은 요청입니다."),

	/**
	 * User
	 */
	USER_FORBIDDEN_ERROR(FORBIDDEN, "USER-000", "사용자의 접근 권한이 없습니다."),
	USER_NOT_FOUND(UNAUTHORIZED, "USER-001", "사용자가 존재하지 않습니다."),

	/**
	 * Couple
	 */
	COUPLE_NOT_FOUND(BAD_REQUEST, "COUPLE-000", "coupleId에 해당하는 Couple이 존재하지 않습니다."),

	/**
	 * Fund
	 */
	FUND_NOT_FOUND(BAD_REQUEST, "FUND-000", "fundId에 해당하는 Fund가 존재하지 않습니다."),
	FUND_NOT_EXIST(BAD_REQUEST, "FUND-001", "부부가 생성한 Fund가 없습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	ErrorCode(final HttpStatus httpStatus, final String code, final String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}
}
