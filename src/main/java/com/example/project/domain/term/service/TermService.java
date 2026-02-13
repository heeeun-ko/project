package com.example.project.domain.term.service;

import com.example.project.domain.term.dto.response.TermSummaryResponseDto;
import com.example.project.domain.term.entities.Term;
import com.example.project.domain.term.enums.TermLevel;
import com.example.project.domain.term.enums.TermSelectType;
import com.example.project.domain.term.repository.TermRepository;
import com.example.project.domain.user.entities.User;
import com.example.project.domain.user.repository.UserRepository;
import com.example.project.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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

  public List<TermSummaryResponseDto> getDailySummaryTerms(Long userId) {

    // 로그인 안한 사용자 → ADMIN_PICK
    if (userId == null) {
      return getAdminPickTerms();
    }

    // 로그인 사용자
    User user = userService.getUser(userId);

    // 사용자 설정에 따른 분기
    if (user.getTermSelectType() == TermSelectType.RANDOM_BY_LEVEL) {
      return getRandomByLevel(user.getPreferredTermLevel());
    }

    return getAdminPickTerms();
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
