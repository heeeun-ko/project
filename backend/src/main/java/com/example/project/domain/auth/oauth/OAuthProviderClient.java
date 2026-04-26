package com.example.project.domain.auth.oauth;

import com.example.project.domain.user.enums.AuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuthProviderClient {

  AuthProvider getProvider();

  OAuthUserInfo getUserInfo(OAuth2User oAuth2User);
}
