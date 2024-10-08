package com.bbyuworld.gagyebbyu.global.error;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	/**
	 * Default
	 */
	SYSTEM_ERROR(INTERNAL_SERVER_ERROR, "SYSTEM-000", "서비스에 장애가 발생했습니다."),
	BAD_REQUEST_ERROR(BAD_REQUEST, "SYSTEM-001", "유효하지 않은 요청입니다."),
	EXPIRED_JWT_TOKEN(UNAUTHORIZED, "SYSTEM-002", "AccessToken 만료"),

	/**
	 * User 관련 에러 코드
	 */
	USER_FORBIDDEN_ERROR(FORBIDDEN, "USER-000", "사용자의 접근 권한이 없습니다."),
	USER_NOT_FOUND(UNAUTHORIZED, "USER-001", "사용자가 존재하지 않습니다."),
	USER_ALREADY_EXIST(BAD_REQUEST, "USER-002", "사용자가 이미 존재합니다."),
	USER_DELETE_ERROR(BAD_REQUEST, "USER-003", "삭제된 사용자 계정입니다."),

	/**
	 * Couple 관련 에러 코드
	 */
	COUPLE_NOT_FOUND(BAD_REQUEST, "COUPLE-000", "coupleId에 해당하는 Couple이 존재하지 않습니다."),

	/**
	 * Fund 관련 에러 코드
	 */
	FUND_NOT_FOUND(BAD_REQUEST, "FUND-000", "fundId에 해당하는 Fund가 존재하지 않습니다."),
	FUND_NOT_EXIST(BAD_REQUEST, "FUND-001", "부부가 생성한 Fund가 없습니다."),
	FUND_EXCEED_EMERGENCY(BAD_REQUEST, "FUND-002", "최대 긴급 출금 횟수를 초과하였습니다."),
	FUND_CHARGE_FAILED(BAD_REQUEST, "FUND-003", "펀딩 입금에 실패했습니다."),

	/**
	 * Expense 관련 에러 코드
	 */
	EXPENSE_NOT_FOUND(BAD_REQUEST, "EXPENSE-000", "expenseId에 해당하는 Expense가 존재하지 않습니다."),
	CATEGORY_NOT_FOUND(BAD_REQUEST, "EXPENSE-001", "해당하는 Category는 없습니다. 알맞게 입력해주세요."),

	/**
	 * Analysis 관련 에러 코드
	 */
	ASSET_NOT_FOUND(BAD_REQUEST, "ANALYSIS-000", "coupleId에 해당하는 Asset이 존재하지 않습니다."),
	LAST_YEAR_ASSET_NOT_FOUND(BAD_REQUEST, "ANALYSIS-001", "coupleId에 해당하는 전년도 AnnualAsset이 존재하지 않습니다."),

	/**
	 * Deposit 관련 에러 코드
	 */
	DEPOSIT_NOT_FOUND(BAD_REQUEST, "DEPOSIT-000", "depositId에 해당하는 Deposit이 존재하지 않습니다."),

	/**
	 * Savings 관련 에러 코드
	 */
	SAVINGS_NOT_FOUND(BAD_REQUEST, "SAVINGS-000", "savingsId에 해당하는 Savings이 존재하지 않습니다."),

	/**
	 * Loan 관련 에러 코드
	 */
	LOAN_NOT_FOUND(BAD_REQUEST, "LOAN-000", "loanId에 해당하는 Loan이 존재하지 않습니다."),

	/**
	 * Account 관련 에러 코드
	 */
	ACCOUNT_NOT_FOUND(BAD_REQUEST, "ACCOUNT-000", "사용자의 해당 계좌가 금융 api 센터에 등록되지 않았습니다."),
	HAVE_NOT_ENOUGH_MONEY(BAD_REQUEST, "ACCOUNT-001", "잔액이 부족합니다."),
	ONE_TIME_TRANSFER_LIMIT(BAD_REQUEST, "ACCOUNT-002", "최대 이체 금액을 넘었습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	ErrorCode(final HttpStatus httpStatus, final String code, final String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}
}
