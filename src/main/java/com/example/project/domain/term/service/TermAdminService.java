package com.example.project.domain.term.service;

import com.example.project.domain.term.dto.request.TermCreateRequestDto;
import com.example.project.domain.term.entities.Term;
import com.example.project.domain.term.repository.TermRepository;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermAdminService {

  private final TermRepository termRepository;

  public Long createTerm(TermCreateRequestDto termCreateRequestDto) {

    if (termRepository.existsByName(termCreateRequestDto.getName())) {
      throw new CustomException(ErrorCodeEnum.TERM_ALREADY_EXISTS);
    }

    Term term = Term.builder()
        .name(termCreateRequestDto.getName())
        .level(termCreateRequestDto.getLevel())
        .summary(termCreateRequestDto.getSummary())
        .description(termCreateRequestDto.getDescription())
        .build();

    termRepository.save(term);

    return term.getId();
  }
}
