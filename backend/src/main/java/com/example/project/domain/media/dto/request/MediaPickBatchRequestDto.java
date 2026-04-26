package com.example.project.domain.media.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class MediaPickBatchRequestDto {

  private List<Long> mediaIds;

}
