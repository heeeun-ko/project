package com.example.project.domain.term.dto.request;

import com.example.project.domain.term.enums.TermLevel;
import lombok.Getter;

@Getter
public class TermCreateRequestDto {

  private String name;
  private TermLevel level;
  private String summary;
  private String description;

}

