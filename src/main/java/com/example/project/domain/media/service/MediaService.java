package com.example.project.domain.media.service;

import com.example.project.domain.media.client.NaverNewsClient;
import com.example.project.domain.media.dto.request.MediaCreateRequestDto;
import com.example.project.domain.media.dto.response.MediaResponseDto;
import com.example.project.domain.media.entities.Media;
import com.example.project.domain.media.repository.MediaRepository;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCode;
import com.example.project.global.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MediaService {

  private final MediaRepository mediaRepository;

  public List<MediaResponseDto> getAllMedia() {
    return mediaRepository.findAll().stream().map(MediaResponseDto::from).toList();
  }

  @Transactional
  public void crateMedia(MediaCreateRequestDto mediaCreateRequestDto) {

    if(mediaRepository.existsByName(mediaCreateRequestDto.getName())) {
      throw new CustomException(ErrorCodeEnum.MEDIA_ALREADY_EXISTS);
    }

    Media media = Media.builder()
        .name(mediaCreateRequestDto.getName())
        .logoUrl(mediaCreateRequestDto.getLogoUrl())
        .build();

    mediaRepository.save(media);
  }


}
