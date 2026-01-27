package com.example.project.domain.user.controller;

import com.example.project.domain.user.dto.request.UpdateProfileRequestDto;
import com.example.project.domain.user.dto.response.UserProfileResponseDto;
import com.example.project.domain.user.service.UserService;
import com.example.project.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<ApiResponse<UserProfileResponseDto>> me(Authentication authentication) {

    Long userId = (Long) authentication.getPrincipal();

    return ResponseEntity.ok(ApiResponse.ok(userService.getProfile(userId)));
  }

  @PatchMapping("/me")
  public ResponseEntity<ApiResponse<UserProfileResponseDto>> updateProfile(
    Authentication authentication,
    @RequestBody UpdateProfileRequestDto updateProfileRequestDto
) {
    Long userId = (Long) authentication.getPrincipal();

    return ResponseEntity.ok(ApiResponse.ok(userService.updateProfile(userId, updateProfileRequestDto)));
  }
}
