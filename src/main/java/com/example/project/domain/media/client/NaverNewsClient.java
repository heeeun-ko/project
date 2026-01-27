package com.example.project.domain.media.client;

import com.example.project.domain.media.dto.response.NaverNewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class NaverNewsClient {

  private final WebClient webClient;

  @Value("${naver.news.client-id}")
  private String clientId;

  @Value("${naver.news.client-secret}")
  private String clientSecret;

  public NaverNewsResponseDto fetchNews(String query, String sort, int start, int display) {

    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/v1/search/news.json")
            .queryParam("query", query)
            .queryParam("sort", sort)   // date | sim
            .queryParam("start", start)
            .queryParam("display", display)
            .build())
        .header("X-Naver-Client-Id", clientId)
        .header("X-Naver-Client-Secret", clientSecret)
        .retrieve()
        .bodyToMono(NaverNewsResponseDto.class)
        .block();
  }
}

