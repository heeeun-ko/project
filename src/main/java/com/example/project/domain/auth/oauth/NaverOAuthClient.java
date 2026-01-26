package com.example.project.domain.auth.oauth;

import com.example.project.domain.user.enums.AuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class NaverOAuthClient implements OAuthProviderClient {

  @Override
  public AuthProvider getProvider() {
    return AuthProvider.NAVER;
  }

  @Override
  @SuppressWarnings("unchecked")
  public OAuthUserInfo getUserInfo(OAuth2User oAuth2User) {

    Map<String, Object> response =
        (Map<String, Object>) oAuth2User.getAttribute("response");

    return OAuthUserInfo.builder()
        .email((String) response.get("email"))
        .name((String) response.get("name"))
        .profileImageUrl((String) response.get("profile_image"))
        .provider(AuthProvider.NAVER)
        .build();
  }
}
