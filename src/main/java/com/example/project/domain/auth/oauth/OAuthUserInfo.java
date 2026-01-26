package com.example.project.domain.auth.oauth;

import com.example.project.domain.user.enums.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OAuthUserInfo {

  private final String email;
  private final String name;
  private final String profileImageUrl;
  private final AuthProvider provider;

}