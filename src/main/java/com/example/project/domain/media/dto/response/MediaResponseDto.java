package com.example.project.domain.media.dto.response;

import com.example.project.domain.media.entities.Media;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MediaResponseDto {

  private Long mediaId;
  private String name;
  private String logoUrl;
  private String domain;

  public static MediaResponseDto from(Media media) {
    return new MediaResponseDto(
        media.getId(),
        media.getName(),
        media.getLogoUrl(),
        media.getDomain()
    );
  }

}
