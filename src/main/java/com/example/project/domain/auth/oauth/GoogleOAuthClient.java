package com.example.project.domain.auth.oauth;

import com.example.project.domain.user.enums.AuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class GoogleOAuthClient implements OAuthProviderClient {

  @Override
  public AuthProvider getProvider() {
    return AuthProvider.GOOGLE;
  }

  @Override
  public OAuthUserInfo getUserInfo(OAuth2User oAuth2User) {
    return OAuthUserInfo.builder()
        .email(oAuth2User.getAttribute("email"))
        .name(oAuth2User.getAttribute("name"))
        .profileImageUrl(oAuth2User.getAttribute("picture"))
        .provider(AuthProvider.GOOGLE)
        .build();
  }

}
