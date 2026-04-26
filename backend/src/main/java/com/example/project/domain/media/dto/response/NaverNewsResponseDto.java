package com.example.project.domain.media.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NaverNewsResponseDto {
  private List<Item> items;

  @Getter
  @NoArgsConstructor
  public static class Item {
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private String originallink;
  }
}