package com.example.project.domain.media.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MediaPickId implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "media_id")
  private Long mediaId;
}
