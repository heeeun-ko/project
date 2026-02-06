package com.example.project.domain.media.dto.response;

import com.example.project.domain.media.enums.NewsCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.util.HtmlUtils;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@AllArgsConstructor
public class NewsPageResponseDto {
  private int page;
  private int size;
  private List<NewsArticleResponseDto> articles;

  @Getter
  @AllArgsConstructor
  public static class NewsArticleResponseDto {

    private String title;
    private String mediaName;
    private LocalDateTime publishedAt;
    private NewsCategory category;
    private String articleUrl;

    public static NewsArticleResponseDto from(
        NaverNewsResponseDto.Item item,
        NewsCategory category
    ) {
      return new NewsArticleResponseDto(
          HtmlUtils.htmlUnescape(item.getTitle()),
          extractMediaName(item.getOriginallink()),
          parsePubDate(item.getPubDate()),
          category,
          item.getOriginallink()
      );
    }

    private static LocalDateTime parsePubDate(String pubDate) {
      return ZonedDateTime
          .parse(pubDate, DateTimeFormatter.RFC_1123_DATE_TIME)
          .toLocalDateTime();
    }

    private static String extractMediaName(String url) {
      return URI.create(url).getHost();
    }
  }
}

