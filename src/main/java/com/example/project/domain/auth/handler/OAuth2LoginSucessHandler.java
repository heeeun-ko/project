package com.example.project.domain.auth.handler;

import com.example.project.domain.auth.jwt.JwtProvider;
import com.example.project.domain.auth.service.AuthService;
import com.example.project.domain.auth.service.OAuthLoginService;
import com.example.project.domain.user.entities.User;
import com.example.project.domain.user.enums.AuthProvider;
import jakarta.servlet.http.Cookie;
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
  private final JwtProvider jwtProvider;
  private final AuthService authService;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) throws IOException {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
    AuthProvider provider = AuthProvider.valueOf(registrationId.toUpperCase());

    // oauth 로그인 or 회원가입
    User user = oAuthLoginService.login(provider, oAuth2User);

    String accessToken = jwtProvider.createAccessToken(user.getId(), user.getUserRole());
    String refreshToken = authService.createRefreshToken(user.getId());

    // Refresh Token → HttpOnly Cookie
    Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
    refreshCookie.setHttpOnly(true);
    refreshCookie.setSecure(false); // 운영에서는 true
    refreshCookie.setPath("/");
    refreshCookie.setMaxAge((int) (60 * 60 * 24 * 7));

    response.addCookie(refreshCookie);

    // Access Token → redirect
    response.sendRedirect(
        "http://localhost:3030/login/success?accessToken=" + accessToken
    );
  }
}