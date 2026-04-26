package com.example.project.domain.user.service;

import com.example.project.domain.user.dto.request.UpdateProfileRequestDto;
import com.example.project.domain.user.dto.response.UserProfileResponseDto;
import com.example.project.domain.user.entities.User;
import com.example.project.domain.user.enums.UserRole;
import com.example.project.domain.user.repository.UserRepository;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;

  public UserProfileResponseDto getProfile(Long userId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCodeEnum.USER_NOT_FOUND));

    return UserProfileResponseDto.from(user);
  }

  @Transactional
  public UserProfileResponseDto updateProfile(Long userId, UpdateProfileRequestDto updateProfileRequestDto) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCodeEnum.USER_NOT_FOUND));

    user.updateProfile(
        updateProfileRequestDto.getName(),
        updateProfileRequestDto.getProfileImageUrl()
    );

    return UserProfileResponseDto.from(user);
  }

  public User getUser(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCodeEnum.USER_NOT_FOUND));
  }

  public User getUserWithRole(Long userId, UserRole userRole) {
    User user = getUser(userId);

    if (user.getUserRole() != userRole) {
      throw new CustomException(ErrorCodeEnum.ACCESS_DENIED);
    }

    return user;
  }

}
