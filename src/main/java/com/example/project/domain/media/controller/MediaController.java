package com.example.project.domain.media.controller;

import com.example.project.domain.media.dto.response.MediaResponseDto;
import com.example.project.domain.media.service.MediaService;
import com.example.project.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
