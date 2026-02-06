package com.example.project.domain.media.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MediaCreateRequestDto {

  private String name;
  private String logoUrl;
  private String domain;

}