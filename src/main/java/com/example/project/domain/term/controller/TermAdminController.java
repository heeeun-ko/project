package com.example.project.domain.term.controller;

import com.example.project.domain.term.dto.request.TermCreateRequestDto;
import com.example.project.domain.term.service.TermAdminService;
import com.example.project.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/terms")
public class TermAdminController {

  private final TermAdminService termAdminService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Long>> createTerm(@RequestBody TermCreateRequestDto termCreateRequestDto) {
    Long termId = termAdminService.createTerm(termCreateRequestDto);

    return ResponseEntity.ok(ApiResponse.ok(termId));
  }

}
