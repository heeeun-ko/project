package com.example.project.domain.user.dto.response;

import com.example.project.domain.user.entities.User;
import com.example.project.domain.user.enums.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMeResponseDto {

  private Long id;
  private String email;
  private String name;
  private String profileImageUrl;
  private AuthProvider authProvider;

  public static UserMeResponseDto from(User user) {
    return new UserMeResponseDto(
        user.getId(),
        user.getEmail(),
        user.getName(),
        user.getProfileImageUrl(),
        user.getAuthProvider()
    );
  }
}
