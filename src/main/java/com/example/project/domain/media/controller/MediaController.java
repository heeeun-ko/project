package com.example.project.domain.media.controller;

import com.example.project.domain.media.dto.request.MediaCreateRequestDto;
import com.example.project.domain.media.dto.response.MediaResponseDto;
import com.example.project.domain.media.service.MediaService;
import com.example.project.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class MediaController {

  private final MediaService mediaService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<MediaResponseDto>>> getAllMedia() {
    return ResponseEntity.ok(ApiResponse.ok(mediaService.getAllMedia()));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> createMedia(
      @RequestBody MediaCreateRequestDto mediaCreateRequestDto
  ) {
    mediaService.crateMedia(mediaCreateRequestDto);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }
}
