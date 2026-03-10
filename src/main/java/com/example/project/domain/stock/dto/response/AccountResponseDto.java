package com.example.project.domain.stock.dto.response;

import com.example.project.domain.stock.entities.Account;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountResponseDto {

  private Long accountId;
  private String broker;
  private String nickname;

  public static AccountResponseDto from(Account account) {
    return AccountResponseDto.builder()
        .accountId(account.getId())
        .broker(account.getBroker())
        .nickname(account.getNickname())
        .build();
  }

}