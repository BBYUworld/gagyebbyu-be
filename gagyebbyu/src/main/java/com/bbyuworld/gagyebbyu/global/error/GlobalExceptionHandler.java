package com.bbyuworld.gagyebbyu.global.error;

import com.bbyuworld.gagyebbyu.global.error.type.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bbyuworld.gagyebbyu.global.error.response.AcceptedResponse;
import com.bbyuworld.gagyebbyu.global.error.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode) {
		return new ResponseEntity<>(ErrorResponse.of(errorCode.getCode(), errorCode.getMessage()),
			errorCode.getHttpStatus());
	}

	private ResponseEntity<AcceptedResponse> createAcceptedResponse(AcceptedCode acceptedCode) {
		return new ResponseEntity<>(AcceptedResponse.of(acceptedCode.getCode(), acceptedCode.getMessage()),
			acceptedCode.getHttpStatus());
	}
	@ExceptionHandler(CustomExpiredJwtTokenException.class)
	public ResponseEntity<String> handleExpiredJwtTokenException(CustomExpiredJwtTokenException e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
		log.error("Error: ", e);
		return new ResponseEntity<>(ErrorResponse.of(HttpStatus.UNAUTHORIZED.toString(), e.getMessage()),
			HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e) {
		log.error("ForbiddenException: ", e);
		return createErrorResponse(e.getErrorCode());
	}

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException e) {
		log.error("DataNotFoundException: ", e);
		return createErrorResponse(e.getErrorCode());
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
		log.error("Error: ", e);
		return createErrorResponse(e.getErrorCode());
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
		log.error("Error: ", e);
		return createErrorResponse(e.getErrorCode());
	}

	@ExceptionHandler(AcceptedException.class)
	public ResponseEntity<AcceptedResponse> handleAcceptedException(AcceptedException e) {
		log.error("AcceptedException: ", e);
		return createAcceptedResponse(e.getAcceptedCode());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		log.error("Error: ", e);
		return createErrorResponse(ErrorCode.SYSTEM_ERROR);
	}
}
