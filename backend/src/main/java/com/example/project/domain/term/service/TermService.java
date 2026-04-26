package com.example.project.domain.term.service;

import com.example.project.domain.term.dto.request.TermSettingRequestDto;
import com.example.project.domain.term.dto.response.TermDetailResponseDto;
import com.example.project.domain.term.dto.response.TermSettingResponseDto;
import com.example.project.domain.term.dto.response.TermSummaryResponseDto;
import com.example.project.domain.term.entities.Term;
import com.example.project.domain.term.enums.TermLevel;
import com.example.project.domain.term.enums.TermSelectType;
import com.example.project.domain.term.repository.TermRepository;
import com.example.project.domain.user.entities.User;
import com.example.project.domain.user.service.UserService;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermService {

  private final TermRepository termRepository;
  private final UserService userService;

  // 데일리 용어 요약 조회
  public List<TermSummaryResponseDto> getDailySummaryTerms(Long userId) {

    // 로그인 안한 사용자 → ADMIN_PICK
    if (userId == null) {
      return getAdminPickTerms();
    }

    User user = userService.getUser(userId);

    // 사용자 설정에 따른 분기
    if (user.getTermSelectType() == TermSelectType.RANDOM_BY_LEVEL) {
      return getRandomByLevel(user.getPreferredTermLevel());
    }

    return getAdminPickTerms();
  }

  // 데일리 용어 상세 조회
  public TermDetailResponseDto getDailyTermDetail(Long userId, Long termId) {
    if (userId == null) {throw new CustomException(ErrorCodeEnum.UNAUTHORIZED);}

    // 사용자 존재 확인 (선택이지만 안전)
    userService.getUser(userId);

    Term term = termRepository.findById(termId)
        .orElseThrow(() -> new CustomException(ErrorCodeEnum.TERM_NOT_FOUND));

    return TermDetailResponseDto.from(term);
  }

  // 용어 설정 조회
  public TermSettingResponseDto getMyTermSetting(Long userId) {
    if (userId == null) {throw new CustomException(ErrorCodeEnum.UNAUTHORIZED);}

    User user = userService.getUser(userId);

    return TermSettingResponseDto.from(user);
  }

  // 용어 설정 수정
  @Transactional
  public void updateMyTermSetting(Long userId, TermSettingRequestDto termSettingRequestDto) {
    if (userId == null) {throw new CustomException(ErrorCodeEnum.UNAUTHORIZED);}

    if (termSettingRequestDto == null ||
        (termSettingRequestDto.getPreferredTermLevel() == null && termSettingRequestDto.getTermSelectType() == null)) {
      throw new CustomException(ErrorCodeEnum.INVALID_REQUEST);
    }

    User user = userService.getUser(userId);

    user.updateTermSetting(termSettingRequestDto.getPreferredTermLevel(), termSettingRequestDto.getTermSelectType());
  }


  /* ========================= */

  private List<TermSummaryResponseDto> getAdminPickTerms() {
    List<TermSummaryResponseDto> result = new ArrayList<>();

    result.addAll(getRandomByLevel(TermLevel.BEGINNER, 1));
    result.addAll(getRandomByLevel(TermLevel.INTERMEDIATE, 1));
    result.addAll(getRandomByLevel(TermLevel.ADVANCED, 1));

    return result;
  }

  private List<TermSummaryResponseDto> getRandomByLevel(TermLevel level) {
    List<Term> terms = termRepository.findRandomByLevel(level.name(), 3);

    return terms.stream()
        .map(TermSummaryResponseDto::from)
        .toList();
  }

  private List<TermSummaryResponseDto> getRandomByLevel(TermLevel level, int count) {
    return termRepository.findRandomByLevel(level.name(), count)
        .stream()
        .map(TermSummaryResponseDto::from)
        .toList();
  }

}
