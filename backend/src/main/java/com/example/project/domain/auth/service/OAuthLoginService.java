package com.example.project.domain.auth.service;

import com.example.project.domain.auth.oauth.OAuthProviderClient;
import com.example.project.domain.auth.oauth.OAuthUserInfo;
import com.example.project.domain.user.entities.User;
import com.example.project.domain.user.enums.AuthProvider;
import com.example.project.domain.user.enums.UserRole;
import com.example.project.domain.user.repository.UserRepository;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {

  private final UserRepository userRepository;
  private final List<OAuthProviderClient> oauthProviderClients;

  public User login(AuthProvider authProvider, OAuth2User oAuth2User) {

    OAuthProviderClient oAuthProviderClient = oauthProviderClients.stream()
        .filter(p -> p.getProvider() == authProvider)
        .findFirst()
        .orElseThrow(() -> new CustomException(ErrorCodeEnum.INVALID_REQUEST));

    OAuthUserInfo oAuthUserInfo = oAuthProviderClient.getUserInfo(oAuth2User);

    // OAuth에서 email 안 내려오는 경우 (동의 안 했을 때)
    if (oAuthUserInfo.getEmail() == null || oAuthUserInfo.getEmail().isBlank()) {
      throw new CustomException(ErrorCodeEnum.INVALID_REQUEST);
    }

    return userRepository.findByEmailAndAuthProvider(oAuthUserInfo.getEmail(), oAuthUserInfo.getProvider())
        .orElseGet(() -> register(oAuthUserInfo));
  }

  private User register(OAuthUserInfo oAuthUserInfo) {
    return userRepository.save(
        User.builder()
            .email(oAuthUserInfo.getEmail())
            .name(oAuthUserInfo.getName())
            .profileImageUrl(oAuthUserInfo.getProfileImageUrl())
            .authProvider(oAuthUserInfo.getProvider())
            .userRole(UserRole.USER)
            .build()
    );
  }

}
