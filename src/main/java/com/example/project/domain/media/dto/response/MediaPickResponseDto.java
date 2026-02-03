package com.example.project.domain.media.dto.response;

import com.example.project.domain.media.entities.Media;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MediaPickResponseDto {

  private Long mediaId;
  private String name;
  private String logoUrl;

  public static MediaPickResponseDto from(Media media) {
    return new MediaPickResponseDto(
        media.getId(),
        media.getName(),
        media.getLogoUrl()
    );
  }
}