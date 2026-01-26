package com.example.project.domain.auth.service;

import com.example.project.domain.auth.jwt.JwtProvider;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

  private final RedisTemplate<String, String> redisTemplate;
  private final JwtProvider jwtProvider;

  // 로그인 성공 시
  public String createRefreshToken(Long userId) {

    String refreshToken = jwtProvider.createRefreshToken(userId);

    long ttlSeconds = JwtProvider.REFRESH_TOKEN_EXPIRE / 1000;
    redisTemplate.opsForValue().set(key(userId), refreshToken, Duration.ofSeconds(ttlSeconds));

    return refreshToken;
  }

  // 재발급 검증
  public Long validateRefreshToken(String refreshToken) {

    Long userId = jwtProvider.getUserId(refreshToken);

    String saved = redisTemplate.opsForValue().get(key(userId));

    if (saved == null || !saved.equals(refreshToken)) {
      throw new CustomException(ErrorCodeEnum.INVALID_TOKEN);
    }

    return userId;
  }

  // 로그아웃
  public void logout(Long userId) {
    redisTemplate.delete(key(userId));
  }

  private String key(Long userId) {
    return "refresh:" + userId;
  }
}

