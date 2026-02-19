package com.example.project.domain.term.dto.request;

import com.example.project.domain.term.enums.TermLevel;
import com.example.project.domain.term.enums.TermSelectType;
import lombok.Getter;

@Getter
public class TermSettingRequestDto {

  private TermLevel preferredTermLevel;
  private TermSelectType termSelectType;

}
