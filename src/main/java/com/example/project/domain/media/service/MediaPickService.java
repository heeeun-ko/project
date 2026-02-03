package com.example.project.domain.media.service;

import com.example.project.domain.media.dto.request.MediaPickBatchRequestDto;
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
import java.util.Set;
import java.util.stream.Collectors;

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

  @Transactional
  public void addMediaPickBatch(Long userId, MediaPickBatchRequestDto mediaPickBatchRequestDto) {
    User user = userService.getUserWithRole(userId, UserRole.USER);

    List<Long> mediaIds = mediaPickBatchRequestDto.getMediaIds();

    if (mediaIds == null || mediaIds.isEmpty()) {
      throw new CustomException(ErrorCodeEnum.MEDIA_PICK_EMPTY_REQUEST);
    }

    // 이미 선택된 mediaId
    Set<Long> alreadyPickedIds = mediaPickRepository.findAllById_UserId(user.getId()).stream()
        .map(pick -> pick.getMedia().getId())
        .collect(Collectors.toSet());

    // 새로 추가할 mediaId만 필터
    List<Long> newMediaIds = mediaIds.stream()
        .filter(id -> !alreadyPickedIds.contains(id))
        .toList();

    if (newMediaIds.isEmpty()) {
      throw new CustomException(ErrorCodeEnum.MEDIA_PICK_ALREADY_ALL);
    }

    // Media 일괄 조회
    List<Media> medias = mediaRepository.findAllById(newMediaIds);
    if (medias.size() != newMediaIds.size()) {
      throw new CustomException(ErrorCodeEnum.MEDIA_NOT_FOUND);
    }

    List<MediaPick> picks = medias.stream()
        .map(media -> new MediaPick(user, media, LocalDateTime.now()))
        .toList();

    mediaPickRepository.saveAll(picks);
  }
}
