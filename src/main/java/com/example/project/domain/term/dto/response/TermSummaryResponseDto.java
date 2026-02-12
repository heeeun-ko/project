package com.example.project.domain.term.dto.response;

import com.example.project.domain.term.entities.Term;
import com.example.project.domain.term.enums.TermLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TermSummaryResponseDto {

  private Long id;
  private String name;
  private TermLevel level;
  private String summary;

  public static TermSummaryResponseDto from(Term term) {
    return TermSummaryResponseDto.builder()
        .id(term.getId())
        .name(term.getName())
        .level(term.getLevel())
        .summary(term.getSummary())
        .build();
  }
}
