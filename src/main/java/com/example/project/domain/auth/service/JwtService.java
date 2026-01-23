package com.example.project.domain.auth.service;

import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  public String createAccessToken(Long userId) {

    if (userId == null) {
      throw new CustomException(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
    }

    // TODO: 실제 JWT 구현
    return "access-token-for-user-" + userId;
  }

}
