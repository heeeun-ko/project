package com.example.project.global.response;

import com.example.project.global.exception.ErrorCode;

public record ApiResponse<T>(
    boolean success,
    T data,
    String message,
    String code
) {

  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>(true, data, null, null);
  }

  public static ApiResponse<?> fail(ErrorCode errorCode) {
    return new ApiResponse<>(false, null, errorCode.message(), errorCode.code());
  }

}