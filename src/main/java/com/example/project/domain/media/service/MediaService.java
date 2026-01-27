package com.example.project.domain.media.service;

import com.example.project.domain.media.dto.response.MediaResponseDto;
import com.example.project.domain.media.repository.MediaRepository;
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


}
