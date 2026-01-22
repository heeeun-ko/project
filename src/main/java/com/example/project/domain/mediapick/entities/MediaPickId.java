package com.example.project.domain.mediapick.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
