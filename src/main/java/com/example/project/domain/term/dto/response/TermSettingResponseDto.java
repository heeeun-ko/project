package com.example.project.domain.term.dto.response;

import com.example.project.domain.term.enums.TermLevel;
import com.example.project.domain.term.enums.TermSelectType;
import com.example.project.domain.user.entities.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TermSettingResponseDto {

  private TermLevel preferredTermLevel;
  private TermSelectType termSelectType;

  public static TermSettingResponseDto from(User user) {
    return TermSettingResponseDto.builder()
        .preferredTermLevel(user.getPreferredTermLevel())
        .termSelectType(user.getTermSelectType())
        .build();
  }
}
