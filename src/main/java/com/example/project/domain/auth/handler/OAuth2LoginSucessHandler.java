package com.example.project.domain.auth.handler;

import com.example.project.domain.auth.service.JwtService;
import com.example.project.domain.auth.service.OAuthLoginService;
import com.example.project.domain.user.entities.User;
import com.example.project.domain.user.enums.AuthProvider;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSucessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final OAuthLoginService oAuthLoginService;
  private final JwtService jwtService;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) throws IOException {

    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

    String registrationId =
        ((OAuth2AuthenticationToken) authentication)
            .getAuthorizedClientRegistrationId();

    AuthProvider authProvider;
    try {
      authProvider = AuthProvider.valueOf(registrationId.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new CustomException(ErrorCodeEnum.INVALID_REQUEST);
    }

    User user = oAuthLoginService.login(authProvider, oAuth2User);

    String accessToken = jwtService.createAccessToken(user.getId());

    response.sendRedirect(
        "http://localhost:3000/login/success?token=" + accessToken
    );
  }
}