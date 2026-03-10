package com.example.project.domain.stock.service;

import com.example.project.domain.stock.dto.request.AccountCreateRequestDto;
import com.example.project.domain.stock.dto.response.AccountResponseDto;
import com.example.project.domain.stock.entities.Account;
import com.example.project.domain.stock.repository.AccountRepository;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

  private final AccountRepository accountRepository;
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
  @Transactional(readOnly = true)
  public List<AccountResponseDto> getAccounts(Long userId) {

    if (userId == null) {throw new CustomException(ErrorCodeEnum.UNAUTHORIZED);}

    List<Account> accounts = accountRepository.findByUserIdAndStatus(userId, Status.ACTIVE);

    return accounts.stream()
        .map(AccountResponseDto::from)
        .toList();
  }

}
