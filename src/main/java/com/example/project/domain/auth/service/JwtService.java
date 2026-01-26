package com.example.project.domain.auth.service;

import com.example.project.domain.auth.jwt.JwtProvider;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final JwtProvider jwtProvider;

  public String createAccessToken(Long userId) {

    if (userId == null) {
      throw new CustomException(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
    }

    return jwtProvider.createAccessToken(userId);
  }

  public String createRefreshToken(Long userId) {

    if (userId == null) {
      throw new CustomException(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
    }

    return jwtProvider.createRefreshToken(userId);
  }

}
