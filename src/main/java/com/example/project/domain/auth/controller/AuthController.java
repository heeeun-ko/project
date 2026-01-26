package com.example.project.domain.auth.controller;

import com.example.project.domain.auth.dto.TokenResponseDto;
import com.example.project.domain.auth.jwt.JwtProvider;
import com.example.project.domain.auth.service.AuthService;
import com.example.project.global.response.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponseDto>> refresh(
            @CookieValue("refreshToken") String refreshToken
    ) {
        Long userId = authService.validateRefreshToken(refreshToken);

        String newAccess = jwtProvider.createAccessToken(userId);

        return ResponseEntity.ok(ApiResponse.ok(new TokenResponseDto(newAccess)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            Authentication authentication,
            HttpServletResponse response
    ) {
        Long userId = (Long) authentication.getPrincipal();
        authService.logout(userId);

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok(ApiResponse.ok(null));
    }

}
