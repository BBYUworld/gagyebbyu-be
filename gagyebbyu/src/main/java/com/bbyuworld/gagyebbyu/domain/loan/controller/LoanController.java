package com.bbyuworld.gagyebbyu.domain.loan.controller;

import com.bbyuworld.gagyebbyu.domain.loan.dto.response.LoanResponseDto;
import com.bbyuworld.gagyebbyu.domain.loan.service.LoanService;
import com.bbyuworld.gagyebbyu.global.jwt.RequireJwtToken;
import com.bbyuworld.gagyebbyu.global.jwt.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    /**
     * 모든 대출 상품 정보 조회
     *
     * @return 모든 대출 상품 반환
     */
    @GetMapping
    public ResponseEntity<List<LoanResponseDto>> getAllLoanProducts() {
        List<LoanResponseDto> loans = loanService.getAllLoanProducts();
        return ResponseEntity.ok(loans);
    }

    /**
     * 대출 이름으로 상품 조회
     *
     * @param loanName 조회할 대출 상품의 이름
     * @return 조회된 대출 상품 정보 반환
     */
    @GetMapping("/loan-name")
    public ResponseEntity<List<LoanResponseDto>> getLoanByLoanName(@RequestParam("loanName") String loanName) {
        List<LoanResponseDto> loan = loanService.getLoanByLoanName(loanName);
        System.out.println("getLoanByLoanName");
        return ResponseEntity.ok(loan);
    }

    /**
     * 특정 은행의 모든 대출 상품을 조회
     *
     * @param bankName 조회할 은행의 이름
     * @return 해당 은행의 모든 대출 상품 정보 목록 반환
     */
    @GetMapping("/bank-name")
    public ResponseEntity<List<LoanResponseDto>> getLoansByBankName(@RequestParam("bankName") String bankName) {
        List<LoanResponseDto> loans = loanService.findLoansByBankName(bankName);
        System.out.println("getLoansByBankName");
        return ResponseEntity.ok(loans);
    }

    /**
     * 대출 금액 범위 내의 대출 상품
     *
     * @param minBalance 최소 대출 금액
     * @param maxBalance 최대 대출 금액
     * @return 해당 금액 범위 내의 대출 상품 정보 목록 반환
     */
    @GetMapping("/balance-range")
    public ResponseEntity<List<LoanResponseDto>> getLoansByBalanceRange(
            @RequestParam Long minBalance,
            @RequestParam Long maxBalance) {
        List<LoanResponseDto> loans = loanService.searchLoansBesideBalance(minBalance, maxBalance);
        System.out.println("getLoansByBalanceRange");
        return ResponseEntity.ok(loans);
    }

    /**
     * 이자율 오름차순으로 정렬
     *
     * @return 이자율 오름차순으로 정렬
     */
    @GetMapping("/ordered-by-rate")
    public ResponseEntity<List<LoanResponseDto>> getLoansOrderedByInterestRate() {
        List<LoanResponseDto> loans = loanService.getOrderedByInterestRate();
        System.out.println("getOrderedByInterestRate");
        return ResponseEntity.ok(loans);
    }

    /**
     * 사용자의 신용정보 호출
     *
     * @return String 으로 사용자의 신용정보 반환
     */
    @GetMapping("/rating")
    @RequireJwtToken
    public ResponseEntity<Map<String, String>> getUserRating() {
        String rating = loanService.getUserRating(UserContext.getUserId());
        Map<String, String> response = new HashMap<>();
        response.put("rating", rating);
        return ResponseEntity.ok(response);
    }
}