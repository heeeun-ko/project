package com.example.project.domain.media.enums;

public class NewsQueryMapper {

  public static String toQuery(NewsCategory category) {
    return switch (category) {
      case STOCK -> "주식 OR 코스피 OR 코스닥";
      case REAL_ESTATE -> "부동산 OR 아파트";
      case COIN -> "가상화폐 OR 비트코인";
      case FX -> "환율 OR 달러";
      case ALL -> "경제";
    };
  }
}

