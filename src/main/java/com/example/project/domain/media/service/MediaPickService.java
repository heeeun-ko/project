package com.example.project.domain.media.service;

import com.example.project.domain.media.dto.request.MediaPickRequestDto;
import com.example.project.domain.media.dto.response.MediaPickResponseDto;
import com.example.project.domain.media.entities.Media;
import com.example.project.domain.media.entities.MediaPick;
import com.example.project.domain.media.repository.MediaPickRepository;
import com.example.project.domain.media.repository.MediaRepository;
import com.example.project.domain.user.entities.User;
import com.example.project.domain.user.enums.UserRole;
import com.example.project.domain.user.service.UserService;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MediaPickService {

  private final MediaPickRepository mediaPickRepository;
  private final MediaRepository mediaRepository;
  private final UserService userService;

  public List<MediaPickResponseDto> getMyMediaPicks(Long userId) {
    User user = userService.getUserWithRole(userId, UserRole.USER);

    return mediaPickRepository.findAllByUserId(user.getId()).stream()
        .map(pick -> MediaPickResponseDto.from(pick.getMedia()))
        .toList();
  }

  @Transactional
  public void addMediaPick(Long userId, MediaPickRequestDto mediaPickRequestDto) {
    User user = userService.getUserWithRole(userId, UserRole.USER);

    if (mediaPickRepository.existsByUserIdAndMediaId(user.getId(), mediaPickRequestDto.getMediaId())) {
      throw new CustomException(ErrorCodeEnum.MEDIA_ALREADY_PICKED);
    }

    Media media = mediaRepository.findById(mediaPickRequestDto.getMediaId())
        .orElseThrow(() -> new CustomException(ErrorCodeEnum.MEDIA_NOT_FOUND));

    mediaPickRepository.save(new MediaPick(user, media, LocalDateTime.now()));
  }
}
