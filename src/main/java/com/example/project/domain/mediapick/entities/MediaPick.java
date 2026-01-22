package com.example.project.domain.mediapick.entities;

import com.example.project.domain.media.entities.Media;
import com.example.project.domain.user.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "media_pick")
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class MediaPick {

  @EmbeddedId
  private MediaPickId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("mediaId")
  @JoinColumn(name = "media_id", nullable = false)
  private Media media;

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public MediaPick(User user, Media media) {
    this.user = user;
    this.media = media;
    this.id = new MediaPickId(user.getId(), media.getId());
  }

}
