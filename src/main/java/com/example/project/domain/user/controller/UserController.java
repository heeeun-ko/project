package com.example.project.domain.user.controller;

import com.example.project.domain.user.dto.response.UserMeResponseDto;
import com.example.project.domain.user.entities.User;
import com.example.project.domain.user.repository.UserRepository;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import com.example.project.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserRepository userRepository;

  @GetMapping("/me")
  public ApiResponse<UserMeResponseDto> me(Authentication authentication) {

    Long userId = (Long) authentication.getPrincipal();

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCodeEnum.USER_NOT_FOUND));

    return ApiResponse.ok(UserMeResponseDto.from(user));
  }
}
