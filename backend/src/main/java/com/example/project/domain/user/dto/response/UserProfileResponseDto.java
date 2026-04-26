package com.example.project.domain.user.dto.response;

import com.example.project.domain.user.entities.User;
import com.example.project.domain.user.enums.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponseDto {

  private Long id;
  private String email;
  private String name;
  private String profileImageUrl;
  private AuthProvider authProvider;

  public static UserProfileResponseDto from(User user) {
    return new UserProfileResponseDto(
        user.getId(),
        user.getEmail(),
        user.getName(),
        user.getProfileImageUrl(),
        user.getAuthProvider()
    );
  }
}
