package com.example.project.domain.term.controller;

import com.example.project.domain.term.dto.request.TermCreateRequestDto;
import com.example.project.domain.term.service.TermAdminService;
import com.example.project.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/terms")
public class TermAdminController {

  private final TermAdminService termAdminService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Long>> createTerm(@RequestBody @Valid TermCreateRequestDto termCreateRequestDto) {
    Long termId = termAdminService.createTerm(termCreateRequestDto);

    return ResponseEntity.ok(ApiResponse.ok(termId));
  }

}
