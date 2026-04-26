package com.example.project.domain.term.dto.response;

import com.example.project.domain.term.entities.Term;
import com.example.project.domain.term.enums.TermLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TermAllResponseDto {

  private Long termId;
  private String name;
  private TermLevel level;
  private String summary;
  private String description;

  public static TermAllResponseDto from(Term term) {
    return new TermAllResponseDto(
        term.getId(),
        term.getName(),
        term.getLevel(),
        term.getSummary(),
        term.getDescription()
    );
  }

}
