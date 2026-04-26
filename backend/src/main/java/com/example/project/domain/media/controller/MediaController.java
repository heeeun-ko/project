package com.example.project.domain.media.controller;

import com.example.project.domain.media.dto.request.MediaCreateRequestDto;
import com.example.project.domain.media.dto.request.MediaPickBatchRequestDto;
import com.example.project.domain.media.dto.request.MediaPickRequestDto;
import com.example.project.domain.media.dto.response.MediaPickResponseDto;
import com.example.project.domain.media.dto.response.MediaResponseDto;
import com.example.project.domain.media.dto.response.NewsPageResponseDto;
import com.example.project.domain.media.enums.NewsCategory;
import com.example.project.domain.media.service.MediaPickService;
import com.example.project.domain.media.service.MediaService;
import com.example.project.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class MediaController {

  private final MediaService mediaService;
  private final MediaPickService mediaPickService;

  /* 언론사 목록 */
  @GetMapping
  public ResponseEntity<ApiResponse<List<MediaResponseDto>>> getAllMedia() {
    return ResponseEntity.ok(ApiResponse.ok(mediaService.getAllMedia()));
  }

  /* 언론사 생성 [ADMIN] */
  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> createMedia(@RequestBody MediaCreateRequestDto mediaCreateRequestDto) {
    mediaService.crateMedia(mediaCreateRequestDto);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }

  /* 관심 언론사 조회 */
  @GetMapping("/picks")
  public ResponseEntity<ApiResponse<List<MediaPickResponseDto>>> getMyPicks(Authentication authentication) {
    Long userId = (Long) authentication.getPrincipal();
    return ResponseEntity.ok(ApiResponse.ok(mediaPickService.getMyMediaPicks(userId))
    );
  }

  /* 관심 언론사 추가(단건) */
  @PostMapping("/picks")
  public ResponseEntity<ApiResponse<Void>> addPick(
      Authentication authentication, @RequestBody MediaPickRequestDto mediaPickRequestDto
  ) {
    Long userId = (Long) authentication.getPrincipal();
    mediaPickService.addMediaPick(userId, mediaPickRequestDto);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }

  /* 관심 언론사 추가(다건) */
  @PostMapping("/picks/batch")
  public ResponseEntity<ApiResponse<Void>> addPicks(
      Authentication authentication, @RequestBody MediaPickBatchRequestDto mediaPickBatchRequestDto
  ) {
    Long userId = (Long) authentication.getPrincipal();
    mediaPickService.addMediaPickBatch(userId, mediaPickBatchRequestDto);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }

  /* 관심 언론사 삭제(다건) */
  @DeleteMapping("/picks")
  public ResponseEntity<ApiResponse<Void>> deletePicks(
      Authentication authentication, @RequestBody MediaPickBatchRequestDto request
  ) {
    Long userId = (Long) authentication.getPrincipal();
    mediaPickService.deleteMediaPickBatch(userId, request);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }

  /* 뉴스 조회 (비로그인) */
  @GetMapping("/news")
  public ResponseEntity<ApiResponse<NewsPageResponseDto>> getAllNews(
      @RequestParam(required = false) Long mediaId,
      @RequestParam(defaultValue = "ALL") NewsCategory category,
      @RequestParam(defaultValue = "latest") String sort,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    return ResponseEntity.ok(ApiResponse.ok(mediaService.getNews(mediaId, category, sort, page, size)));
  }


}
