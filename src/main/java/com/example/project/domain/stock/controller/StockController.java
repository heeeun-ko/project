package com.example.project.domain.stock.controller;

import com.example.project.domain.stock.dto.request.AccountCreateRequestDto;
import com.example.project.domain.stock.dto.response.AccountResponseDto;
import com.example.project.domain.stock.service.StockService;
import com.example.project.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StockController {

  private final StockService stockService;

  /* ============ ACCOUNTS ============= */
  /* 계좌 생성 */
  @PostMapping("/accounts")
  public ResponseEntity<ApiResponse<Long>> createAccount(
      Authentication authentication, @RequestBody AccountCreateRequestDto accountCreateRequestDto
  ) {
    Long userId = (Long) authentication.getPrincipal();
    return ResponseEntity.ok(ApiResponse.ok(stockService.createAccount(userId, accountCreateRequestDto)));
  }

  /* 계좌 목록 조회 */
  @GetMapping("/accounts")
  public ResponseEntity<ApiResponse<List<AccountResponseDto>>> getAccounts(
      Authentication authentication
  ) {
    Long userId = (Long) authentication.getPrincipal();
    return ResponseEntity.ok(ApiResponse.ok(stockService.getAccounts(userId)));
  }

}
