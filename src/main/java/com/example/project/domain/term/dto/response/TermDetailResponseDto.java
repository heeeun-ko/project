package com.example.project.domain.term.dto.response;

import com.example.project.domain.term.entities.Term;
import com.example.project.domain.term.enums.TermLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TermDetailResponseDto {

  private Long id;
  private String name;
  private TermLevel level;
  private String description;

  public static TermDetailResponseDto from(Term term) {
    return TermDetailResponseDto.builder()
        .id(term.getId())
        .name(term.getName())
        .level(term.getLevel())
        .description(term.getDescription())
        .build();
  }
}