package com.example.project.domain.auth.service;

import com.example.project.domain.auth.jwt.JwtProvider;
import com.example.project.domain.user.enums.UserRole;
import com.example.project.domain.user.repository.UserRepository;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

  private final StringRedisTemplate stringRedisTemplate;
  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;

  // 로그인 성공 시
  public String createRefreshToken(Long userId) {
    String refreshToken = jwtProvider.createRefreshToken(userId);

    long ttlSeconds = JwtProvider.REFRESH_TOKEN_EXPIRE / 1000;
    stringRedisTemplate.opsForValue().set(key(userId), refreshToken, Duration.ofSeconds(ttlSeconds));

    return refreshToken;
  }

  // 재발급 검증
  public Long validateRefreshToken(String refreshToken) {
    Long userId = jwtProvider.getUserId(refreshToken);

    String saved = stringRedisTemplate.opsForValue().get(key(userId));

    if (saved == null || !saved.equals(refreshToken)) {
      throw new CustomException(ErrorCodeEnum.INVALID_TOKEN);
    }

    return userId;
  }

  public String reissueAccessToken(String refreshToken) {
    Long userId = validateRefreshToken(refreshToken);

    UserRole role = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCodeEnum.USER_NOT_FOUND))
        .getUserRole();

    return jwtProvider.createAccessToken(userId, role);
  }

  // 로그아웃
  public void logout(Long userId) {
    stringRedisTemplate.delete(key(userId));
  }

  private String key(Long userId) {
    return "refresh:" + userId;
  }

}

