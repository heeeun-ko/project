package com.example.project.domain.term.dto.request;

import com.example.project.domain.term.enums.TermLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TermCreateRequestDto {

  @NotBlank
  private String name;

  @NotNull
  private TermLevel level;

  @NotBlank
  private String summary;

  @NotBlank
  private String description;

}

