package com.example.project.domain.term.controller;

import com.example.project.domain.term.dto.request.TermCreateRequestDto;
import com.example.project.domain.term.dto.response.TermAllResponseDto;
import com.example.project.domain.term.dto.response.TermDetailResponseDto;
import com.example.project.domain.term.dto.response.TermSummaryResponseDto;
import com.example.project.domain.term.service.TermAdminService;
import com.example.project.domain.term.service.TermService;
import com.example.project.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/terms")
public class TermController {

  private final TermAdminService termAdminService;
  private final TermService termService;

  /* ============ ADMIN ============= */
  /* 용어 생성 */
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Long>> createTerm(@RequestBody TermCreateRequestDto termCreateRequestDto) {
    Long termId = termAdminService.createTerm(termCreateRequestDto);

    return ResponseEntity.ok(ApiResponse.ok(termId));
  }

  /* 용어 전체 조회 */
  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<TermAllResponseDto>>> getAllTerms() {
    return ResponseEntity.ok(ApiResponse.ok(termAdminService.getAllTerms()));
  }

  /* 용어 수정 */
  @PutMapping("/{termId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Long>> updateTerm(
      @PathVariable Long termId, @RequestBody TermCreateRequestDto termCreateRequestDto
  ){
    Long updatedTermId = termAdminService.updateTerm(termId, termCreateRequestDto);
    return ResponseEntity.ok(ApiResponse.ok(updatedTermId));
  }

  /* 용어 삭제 */
  @DeleteMapping("/{termId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> deleteTerm(@PathVariable Long termId) {
    termAdminService.deleteTerm(termId);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }

  /* ============ USER ============= */
  /* 데일리 용어 요약 조회 */
  @GetMapping("/daily")
  public ResponseEntity<ApiResponse<List<TermSummaryResponseDto>>> getDailyTerms(Authentication authentication) {
    Long userId = null;

    if (authentication != null && authentication.getPrincipal() instanceof Long) {
      userId = (Long) authentication.getPrincipal();
    }

    return ResponseEntity.ok(ApiResponse.ok(termService.getDailySummaryTerms(userId)));
  }

  /* 데일리 용어 상세 조회 */
  @GetMapping("/daily/{termId}")
  public ResponseEntity<ApiResponse<TermDetailResponseDto>> getDailyTermDetail(
      @PathVariable Long termId, Authentication authentication
  ) {
    Long userId = (Long) authentication.getPrincipal();

    return ResponseEntity.ok(ApiResponse.ok(termService.getDailyTermDetail(userId, termId)));
  }
}
