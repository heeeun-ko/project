package com.example.project.domain.media.service;

import com.example.project.domain.media.client.NaverNewsClient;
import com.example.project.domain.media.dto.request.MediaCreateRequestDto;
import com.example.project.domain.media.dto.response.MediaResponseDto;
import com.example.project.domain.media.dto.response.NaverNewsResponseDto;
import com.example.project.domain.media.dto.response.NewsPageResponseDto;
import com.example.project.domain.media.entities.Media;
import com.example.project.domain.media.enums.NewsCategory;
import com.example.project.domain.media.enums.NewsQueryMapper;
import com.example.project.domain.media.repository.MediaRepository;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MediaService {

  private final NaverNewsClient naverNewsClient;
  private final MediaRepository mediaRepository;

  // 언론사 전체 조회
  public List<MediaResponseDto> getAllMedia() {
    return mediaRepository.findAll().stream().map(MediaResponseDto::from).toList();
  }

  // 언론사 생성 [ admin ]
  @Transactional
  public void crateMedia(MediaCreateRequestDto mediaCreateRequestDto) {
    if(mediaRepository.existsByName(mediaCreateRequestDto.getName())) {
      throw new CustomException(ErrorCodeEnum.MEDIA_ALREADY_EXISTS);
    }

    if (mediaRepository.existsByDomain(mediaCreateRequestDto.getDomain())) {
      throw new CustomException(ErrorCodeEnum.MEDIA_DOMAIN_ALREADY_EXISTS);
    }

    Media media = Media.builder()
        .name(mediaCreateRequestDto.getName())
        .logoUrl(mediaCreateRequestDto.getLogoUrl())
        .domain(mediaCreateRequestDto.getDomain())
        .build();

    mediaRepository.save(media);
  }

  // 뉴스 조회 (비로그인)
  public NewsPageResponseDto getNews(
      Long mediaId, NewsCategory newsCategory, String sort, int page, int size
  ) {
    String query = NewsQueryMapper.toQuery(newsCategory);
    int start = page * size + 1;
    int display = mediaId == null ? size : size * 3; // mediaId가 있으면 필터 대비해서 더 많이 가져옴

    NaverNewsResponseDto naverNewsResponseDto =
        naverNewsClient.searchNews(query, sort.equals("popular") ? "sim" : "date", start, display);

    Media media;
    if(mediaId != null) {
      media = mediaRepository.findById(mediaId)
          .orElseThrow(() -> new CustomException(ErrorCodeEnum.MEDIA_NOT_FOUND));
    } else {
      media = null;
    }

    List<NewsPageResponseDto.NewsArticleResponseDto> articles =
        naverNewsResponseDto.getItems().stream()
            .filter(item -> filterByMedia(media, item))
            .map(item -> NewsPageResponseDto.NewsArticleResponseDto.from(item, newsCategory))
            .limit(size)
            .toList();

    return new NewsPageResponseDto(page, size, articles);
  }

  // 내부 필터링
  private boolean filterByMedia(Media media, NaverNewsResponseDto.Item item) {
    if (media == null) return true;

    String url = item.getOriginallink() != null
        ? item.getOriginallink()
        : item.getLink();

    return url.contains(media.getDomain());
  }



}
