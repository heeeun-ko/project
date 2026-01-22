package com.example.project.domain.scrap.entities;

import com.example.project.domain.scrap.enums.ScrapTargetType;
import com.example.project.domain.user.entities.User;
import com.example.project.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
  name = "scrap",
  uniqueConstraints = {
      @UniqueConstraint(  // “이 조합은 절대 중복되면 안 된다”를 DB에 선언
          name = "unique_user_target",
          columnNames = {"user_id", "target_type", "target_link"}
      )
  }
)
@SuperBuilder
@NoArgsConstructor
@Getter
public class Scrap extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(name = "target_type", nullable = false)
  private ScrapTargetType targetType;  // NEWS / TERM / YOUTUBE

  @Column(name = "target_link", nullable = false)
  private String targetLink;

  @Column(nullable = false)
  private String title;

  @Column(name = "source_name", nullable = false)
  private String sourceName;  // EX. 언론사명 / 용어(고정) / 채널명

  public static Scrap news(User user, String newsUrl, String title, String mediaName) {
    return new Scrap(user, ScrapTargetType.NEWS, newsUrl, title, mediaName);
  }

  public static Scrap term(User user, Long termId, String termName) {
    return new Scrap(user, ScrapTargetType.TERM, termId.toString(), termName, "TERM");
  }

  public static Scrap youtube(User user, String videoId, String title, String channelName) {
    return new Scrap(user, ScrapTargetType.YOUTUBE, videoId, title, channelName);
  }

  private Scrap(
      User user,
      ScrapTargetType targetType,
      String targetLink,
      String title,
      String sourceName
  ) {
    this.user = user;
    this.targetType = targetType;
    this.targetLink = targetLink;
    this.title = title;
    this.sourceName = sourceName;
  }
}
