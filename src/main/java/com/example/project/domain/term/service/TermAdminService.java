package com.example.project.domain.term.service;

import com.example.project.domain.term.dto.request.TermCreateRequestDto;
import com.example.project.domain.term.dto.response.TermAllResponseDto;
import com.example.project.domain.term.entities.Term;
import com.example.project.domain.term.repository.TermRepository;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermAdminService {

  private final TermRepository termRepository;

  // 용어 생성
  @Transactional
  public Long createTerm(TermCreateRequestDto termCreateRequestDto) {
    validateCreateRequest(termCreateRequestDto);

    if (termRepository.existsByName(termCreateRequestDto.getName())) {
      throw new CustomException(ErrorCodeEnum.TERM_ALREADY_EXISTS);
    }

    Term term = Term.create(
        termCreateRequestDto.getName(),
        termCreateRequestDto.getLevel(),
        termCreateRequestDto.getSummary(),
        termCreateRequestDto.getDescription()
    );

    termRepository.save(term);
    return term.getId();
  }

  // 용어 전체 조회
  public List<TermAllResponseDto> getAllTerms() {
    return termRepository.findAll().stream().map(TermAllResponseDto::from).toList();
  }

  // 용어 수정
  @Transactional
  public Long updateTerm(Long termId, TermCreateRequestDto termCreateRequestDto) {
    validateUpdateRequest(termCreateRequestDto);

    Term term = termRepository.findById(termId)
        .orElseThrow(() -> new CustomException(ErrorCodeEnum.TERM_NOT_FOUND));

    if (termCreateRequestDto.getName() != null && !termCreateRequestDto.getName().isBlank()
        && !termCreateRequestDto.getName().equals(term.getName())
        && termRepository.existsByName(termCreateRequestDto.getName())) {
      throw new CustomException(ErrorCodeEnum.TERM_ALREADY_EXISTS);
    }

    term.update(
        termCreateRequestDto.getName(),
        termCreateRequestDto.getLevel(),
        termCreateRequestDto.getSummary(),
        termCreateRequestDto.getDescription()
    );

    termRepository.save(term);
    return term.getId();
  }

  // 용어 삭제
  @Transactional
  public void deleteTerm(Long termId) {
    Term term = termRepository.findById(termId)
        .orElseThrow(() -> new CustomException(ErrorCodeEnum.TERM_NOT_FOUND));

    termRepository.delete(term);
  }


  /* ========= validation ========= */
  private void validateCreateRequest(TermCreateRequestDto termCreateRequestDto) {
    if (termCreateRequestDto == null
        || isBlank(termCreateRequestDto.getName())
        || termCreateRequestDto.getLevel() == null
        || isBlank(termCreateRequestDto.getSummary())
        || isBlank(termCreateRequestDto.getDescription())) {
      throw new CustomException(ErrorCodeEnum.INVALID_REQUEST);
    }
  }

  private void validateUpdateRequest(TermCreateRequestDto termCreateRequestDto) {
    boolean noChange = termCreateRequestDto == null
        || (isBlank(termCreateRequestDto.getName())
        && termCreateRequestDto.getLevel() == null
        && isBlank(termCreateRequestDto.getSummary())
        && isBlank(termCreateRequestDto.getDescription()));

    if (noChange) {
      throw new CustomException(ErrorCodeEnum.INVALID_REQUEST);
    }
  }

  private boolean isBlank(String value) {
    return value == null || value.isBlank();
  }

}
