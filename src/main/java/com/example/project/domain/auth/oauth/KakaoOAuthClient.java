package com.example.project.domain.auth.oauth;

import com.example.project.domain.user.enums.AuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KakaoOAuthClient implements OAuthProviderClient {


  @Override
  public AuthProvider getProvider() {
    return AuthProvider.KAKAO;
  }

  @Override
  public OAuthUserInfo getUserInfo(OAuth2User oAuth2User) {

    Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");

    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

    return new OAuthUserInfo(
        (String) kakaoAccount.get("email"),
        (String) profile.get("nickname"),
        (String) profile.get("profile_image_url"),
        AuthProvider.KAKAO
    );
  }

}
