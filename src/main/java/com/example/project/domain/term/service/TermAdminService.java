package com.example.project.domain.term.service;

import com.example.project.domain.media.dto.response.MediaResponseDto;
import com.example.project.domain.term.dto.request.TermCreateRequestDto;
import com.example.project.domain.term.dto.response.TermAllResponseDto;
import com.example.project.domain.term.entities.Term;
import com.example.project.domain.term.repository.TermRepository;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermAdminService {

  private final TermRepository termRepository;

  @Transactional
  public Long createTerm(TermCreateRequestDto termCreateRequestDto) {
    if (termCreateRequestDto == null
        || termCreateRequestDto.getName() == null || termCreateRequestDto.getName().isBlank()
        || termCreateRequestDto.getLevel() == null
        || termCreateRequestDto.getSummary() == null || termCreateRequestDto.getSummary().isBlank()
        || termCreateRequestDto.getDescription() == null || termCreateRequestDto.getDescription().isBlank()) {
      throw new CustomException(ErrorCodeEnum.INVALID_REQUEST);
    }

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

  public List<TermAllResponseDto> getAllTerms() {
    return termRepository.findAll().stream().map(TermAllResponseDto::from).toList();
  }

}
