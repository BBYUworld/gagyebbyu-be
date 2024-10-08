package com.bbyuworld.gagyebbyu.domain.couple.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbyuworld.gagyebbyu.domain.couple.entity.dto.request.CoupleConnectDto;
import com.bbyuworld.gagyebbyu.domain.couple.entity.dto.request.CoupleCreateDto;
import com.bbyuworld.gagyebbyu.domain.couple.entity.dto.request.CoupleUpdateDto;
import com.bbyuworld.gagyebbyu.domain.couple.entity.dto.response.CoupleResponseDto;
import com.bbyuworld.gagyebbyu.domain.couple.service.CoupleService;
import com.bbyuworld.gagyebbyu.global.jwt.RequireJwtToken;
import com.bbyuworld.gagyebbyu.global.jwt.UserContext;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/couple")
@RequiredArgsConstructor
public class CoupleController {

	private final CoupleService coupleService;

	/**
	 * 커플 등록 요청
	 * @param coupleCreateDto
	 * @return
	 */
	@PostMapping(path = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createCoupleRequest(@RequestBody CoupleCreateDto coupleCreateDto) {
		Long coupleId = coupleService.createCouple(coupleCreateDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(coupleId);
	}

	/**
	 * 커플 연결
	 * @param coupleConnectDto
	 * @return
	 */
	@PostMapping(path = "/connect/{coupleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> connectCouple(@PathVariable Long coupleId,
		@RequestBody CoupleConnectDto coupleConnectDto) {
		coupleService.connectCouple(coupleId, coupleConnectDto);
		return ResponseEntity.ok().build();
	}

	/**
	 * 커플 프로필 수정
	 * @param coupleUpdateDto
	 * @return
	 */
	@PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@RequireJwtToken
	public ResponseEntity<Void> updateCouple(
		@RequestBody CoupleUpdateDto coupleUpdateDto) {
		coupleService.updateCouple(UserContext.getUserId(), coupleUpdateDto);
		return ResponseEntity.ok().build();
	}

	/**
	 * 커플 조회
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@RequireJwtToken
	public ResponseEntity<CoupleResponseDto> updateCouple() {
		return ResponseEntity.ok(coupleService.getCouple(UserContext.getUserId()));
	}

	@GetMapping("/{userId}")
	@RequireJwtToken
	public ResponseEntity<CoupleResponseDto> updateCouple(@PathVariable Long userId) {
		System.out.println("Get Couple Id user Id = "+userId);
		return ResponseEntity.ok(coupleService.getCouple(userId));
	}

	/**
	 * 커플 조회 in LoanPage
	 * @return
	 */
	@GetMapping("/loan")
	@RequireJwtToken
	public ResponseEntity<CoupleResponseDto> getCoupleInLoan() {
		return ResponseEntity.ok(coupleService.getCoupleinLoan(UserContext.getUserId()));
	}
}