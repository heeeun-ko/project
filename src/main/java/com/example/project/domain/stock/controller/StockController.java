package com.example.project.domain.stock.controller;

import com.example.project.domain.stock.dto.request.AccountCreateRequestDto;
import com.example.project.domain.stock.dto.request.StockTradeRequestDto;
import com.example.project.domain.stock.dto.response.AccountResponseDto;
import com.example.project.domain.stock.dto.response.StockTransactionResponseDto;
import com.example.project.domain.stock.entities.StockTransaction;
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
  public ResponseEntity<ApiResponse<List<AccountResponseDto>>> getAccounts(Authentication authentication) {
    Long userId = (Long) authentication.getPrincipal();
    return ResponseEntity.ok(ApiResponse.ok(stockService.getAccounts(userId)));
  }

  /* 계좌 수정 */
  @PatchMapping("/accounts/{accountId}")
  public ResponseEntity<ApiResponse<AccountResponseDto>> updateAccount(
      Authentication authentication,
      @PathVariable Long accountId,
      @RequestBody AccountCreateRequestDto accountCreateRequestDto
  ) {
    Long userId = (Long) authentication.getPrincipal();
    return ResponseEntity.ok(ApiResponse.ok(stockService.updateAccount(userId, accountId, accountCreateRequestDto)));
  }

  /* 계좌 삭제 */
  @DeleteMapping("/accounts/{accountId}")
  public ResponseEntity<ApiResponse<Void>> deleteAccount(
      Authentication authentication,@PathVariable Long accountId
  ) {
    Long userId = (Long) authentication.getPrincipal();
    stockService.deleteAccount(userId, accountId);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }


  /* ============ STOCK ============= */
  /* 주식 매수 입력 */
  @PostMapping("/stocks/buy")
  public ResponseEntity<ApiResponse<Void>> buyStock(
      Authentication authentication, @RequestBody StockTradeRequestDto stockTradeRequestDto
  ) {
    Long userId = (Long) authentication.getPrincipal();
    stockService.buyStock(userId, stockTradeRequestDto);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }

  /* 주식 매도 입력 */
  @PostMapping("/stocks/sell")
  public ResponseEntity<ApiResponse<Void>> sellStock(
      Authentication authentication, @RequestBody StockTradeRequestDto stockTradeRequestDto
  ) {
    Long userId = (Long) authentication.getPrincipal();
    stockService.sellStock(userId, stockTradeRequestDto);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }

  /* 주식 거래 기록 조회 */
  @GetMapping("/stocks/transactions")
  public ResponseEntity<ApiResponse<List<StockTransactionResponseDto>>> getTransactions(
      Authentication authentication,
      @RequestParam(required = false) Long accountId,
      @RequestParam(required = false) String symbol
  ) {
    Long userId = (Long) authentication.getPrincipal();
    return ResponseEntity.ok(ApiResponse.ok(stockService.getTransactions(userId, accountId, symbol)));
  }

}
