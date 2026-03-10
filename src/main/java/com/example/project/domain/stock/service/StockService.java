package com.example.project.domain.stock.service;

import com.example.project.domain.stock.dto.request.AccountCreateRequestDto;
import com.example.project.domain.stock.dto.request.StockBuyRequestDto;
import com.example.project.domain.stock.dto.response.AccountResponseDto;
import com.example.project.domain.stock.entities.Account;
import com.example.project.domain.stock.entities.StockHolding;
import com.example.project.domain.stock.entities.StockTransaction;
import com.example.project.domain.stock.enums.StockTransactionType;
import com.example.project.domain.stock.repository.AccountRepository;
import com.example.project.domain.stock.repository.StockHoldingRepository;
import com.example.project.domain.stock.repository.StockTransactionRepository;
import com.example.project.domain.user.entities.User;
import com.example.project.domain.user.enums.UserRole;
import com.example.project.domain.user.service.UserService;
import com.example.project.global.enums.Status;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

  private final AccountRepository accountRepository;
  private final StockHoldingRepository stockHoldingRepository;
  private final StockTransactionRepository stockTransactionRepository;
  private final UserService userService;

  /* ============ ACCOUNTS ============= */
  /* 계좌 생성 */
  @Transactional
  public Long createAccount(Long userId, AccountCreateRequestDto accountCreateRequestDto) {

    if (userId == null) {throw new CustomException(ErrorCodeEnum.UNAUTHORIZED);}

    User user = userService.getUserWithRole(userId, UserRole.USER);

    Account account = Account.builder()
        .user(user)
        .broker(accountCreateRequestDto.getBroker())
        .nickname(accountCreateRequestDto.getNickname())
        .build();

    accountRepository.save(account);

    return account.getId();
  }

  /* 계좌 목록 조회 */
  public List<AccountResponseDto> getAccounts(Long userId) {

    if (userId == null) {throw new CustomException(ErrorCodeEnum.UNAUTHORIZED);}

    List<Account> accounts = accountRepository.findByUserIdAndStatus(userId, Status.ACTIVE);

    return accounts.stream()
        .map(AccountResponseDto::from)
        .toList();
  }

  /* 계좌 수정 */
  @Transactional
  public AccountResponseDto updateAccount(Long userId, Long accountId, AccountCreateRequestDto accountCreateRequestDto) {

    if (userId == null) {throw new CustomException(ErrorCodeEnum.UNAUTHORIZED);}

    Account account = accountRepository.findByIdAndStatus(accountId, Status.ACTIVE)
        .orElseThrow(() -> new CustomException(ErrorCodeEnum.ACCOUNT_NOT_FOUND));

    if (!account.getUser().getId().equals(userId)) {
      throw new CustomException(ErrorCodeEnum.UNAUTHORIZED);
    }

    if (accountCreateRequestDto.getBroker() != null) {
      account.setBroker(accountCreateRequestDto.getBroker());
    }

    if (accountCreateRequestDto.getNickname() != null) {
      account.setNickname(accountCreateRequestDto.getNickname());
    }

    return AccountResponseDto.from(account);
  }

  /* 계좌 삭제 */
  @Transactional
  public void deleteAccount(Long userId, Long accountId) {

    if (userId == null) {throw new CustomException(ErrorCodeEnum.UNAUTHORIZED);}

    Account account = accountRepository.findByIdAndStatus(accountId, Status.ACTIVE)
        .orElseThrow(() -> new CustomException(ErrorCodeEnum.ACCOUNT_NOT_FOUND));

    if (!account.getUser().getId().equals(userId)) {throw new CustomException(ErrorCodeEnum.UNAUTHORIZED);}

    account.deactivate();
  }


  /* ============ STOCK ============= */
  /* 주식 매수 입력 */
  @Transactional
  public void buyStock(Long userId, StockBuyRequestDto stockBuyRequestDto) {

    User user = userService.getUserWithRole(userId, UserRole.USER);

    Account account = accountRepository.findByIdAndStatus(stockBuyRequestDto.getAccountId(), Status.ACTIVE)
        .orElseThrow(() -> new CustomException(ErrorCodeEnum.ACCOUNT_NOT_FOUND));

    if (!account.getUser().getId().equals(user.getId())) {
      throw new CustomException(ErrorCodeEnum.ACCESS_DENIED);
    }

    // 거래 기록 생성
    StockTransaction stockTransaction = StockTransaction.create(
        account,
        stockBuyRequestDto.getSymbol(),
        stockBuyRequestDto.getName(),
        StockTransactionType.BUY,
        stockBuyRequestDto.getPrice(),
        stockBuyRequestDto.getQuantity(),
        stockBuyRequestDto.getTransactionDate()
    );

    stockTransactionRepository.save(stockTransaction);

    // 보유 종목 조회
    Optional<StockHolding> optionalStockHolding =
        stockHoldingRepository.findByAccountIdAndSymbolAndStatus(account.getId(), stockBuyRequestDto.getSymbol(), Status.ACTIVE);

    if (optionalStockHolding.isPresent()) {
      StockHolding stockHolding = optionalStockHolding.get();
      stockHolding.buy(stockBuyRequestDto.getPrice(), stockBuyRequestDto.getQuantity());
    } else {
      StockHolding stockHolding = StockHolding.create(
          account,
          stockBuyRequestDto.getSymbol(),
          stockBuyRequestDto.getName(),
          stockBuyRequestDto.getQuantity(),
          stockBuyRequestDto.getPrice()
      );
      stockHoldingRepository.save(stockHolding);
    }
  }

}
