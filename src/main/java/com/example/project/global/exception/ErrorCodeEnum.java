package com.example.project.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCodeEnum implements ErrorCode {

  // ===== Global =====
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Global_500", "서버 오류가 발생했습니다."),
  INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Global_400", "잘못된 요청입니다."),
  INVALID_ENUM_VALUE(HttpStatus.BAD_REQUEST,"GLOBAL_400_ENUM","잘못된 enum 값입니다."),

  // ===== Auth =====
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_401", "인증이 필요합니다."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_401_1", "유효하지 않은 토큰입니다."),
  EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_401_2", "만료된 토큰입니다."),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTH_403", "접근 권한이 없습니다."),

  // ===== User =====
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404", "사용자를 찾을 수 없습니다."),
  USER_BLOCKED(HttpStatus.FORBIDDEN, "USER_403", "차단된 사용자입니다."),

  // ===== MEDIA =====
  MEDIA_NOT_FOUND(HttpStatus.NOT_FOUND, "MEDIA_404", "언론사를 찾을 수 없습니다."),
  MEDIA_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEDIA_409", "이미 등록된 언론사입니다."),
  MEDIA_CREATE_FORBIDDEN(HttpStatus.FORBIDDEN, "MEDIA_403", "언론사 등록 권한이 없습니다."),
  MEDIA_DOMAIN_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEDIA_409_2","이미 등록된 언론사 도메인입니다."),

  // ===== Media Pick =====
  MEDIA_PICK_FORBIDDEN(HttpStatus.FORBIDDEN, "MEDIA_PICK_403", "일반 사용자만 관심 언론사를 설정할 수 있습니다."),
  MEDIA_ALREADY_PICKED(HttpStatus.CONFLICT, "MEDIA_PICK_409", "이미 관심 언론사로 등록되어 있습니다."),
  MEDIA_PICK_EMPTY_REQUEST(HttpStatus.BAD_REQUEST, "MEDIA_PICK_400", "선택한 언론사가 없습니다."),
  MEDIA_PICK_ALREADY_ALL(HttpStatus.CONFLICT, "MEDIA_PICK_409_ALL", "이미 모든 언론사가 관심 언론사로 등록되어 있습니다."),
  MEDIA_PICK_NOT_FOUND(HttpStatus.NOT_FOUND, "MEDIA_PICK_404", "관심 언론사를 찾을 수 없습니다."),

  // ===== Term =====
  TERM_NOT_FOUND(HttpStatus.NOT_FOUND, "TERM_404", "용어를 찾을 수 없습니다."),
  TERM_ALREADY_EXISTS(HttpStatus.CONFLICT, "TERM_409", "이미 등록된 용어입니다."),
  TERM_CREATE_FORBIDDEN(HttpStatus.FORBIDDEN, "TERM_403", "용어 등록 권한이 없습니다.")

  ;


  private final HttpStatus status;
  private final String code;
  private final String message;

  ErrorCodeEnum(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

  @Override public HttpStatus status() { return status; }
  @Override public String code() { return code; }
  @Override public String message() { return message; }
}