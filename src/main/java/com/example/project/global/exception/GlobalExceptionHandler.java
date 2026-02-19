package com.example.project.global.exception;

import com.example.project.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ApiResponse<?>> handleCustom(CustomException e) {
    ErrorCode errorCode = e.getErrorCode();
    return ResponseEntity
        .status(errorCode.status())
        .body(ApiResponse.fail(errorCode));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<?>> handle(Exception e) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.fail(ErrorCodeEnum.INTERNAL_SERVER_ERROR));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<?>> handleInvalidEnum(HttpMessageNotReadableException e) {
    Throwable cause = e.getCause();

    if (cause instanceof InvalidFormatException ex && ex.getTargetType().isEnum()) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(ApiResponse.fail(ErrorCodeEnum.INVALID_ENUM_VALUE));
    }

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.fail(ErrorCodeEnum.INVALID_REQUEST));
  }

}
