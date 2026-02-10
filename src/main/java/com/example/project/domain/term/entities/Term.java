package com.example.project.domain.term.entities;

import com.example.project.domain.term.enums.TermLevel;
import com.example.project.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "term")
@SuperBuilder
@NoArgsConstructor
@Getter
public class Term extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TermLevel level;  // 초급 / 중급 / 상급

  @Column(nullable = false, length = 200)
  private String summary;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;


  /* ========= 생성 ========= */
  public static Term create(String name, TermLevel level, String summary, String description) {
    return Term.builder()
        .name(name)
        .level(level)
        .summary(summary)
        .description(description)
        .build();
  }

  /* ========= 수정 ========= */
  public void update(String name, TermLevel level, String summary, String description) {
    if (name != null && !name.isBlank()) {
      this.name = name;
    }
    if (level != null) {
      this.level = level;
    }
    if (summary != null && !summary.isBlank()) {
      this.summary = summary;
    }
    if (description != null && !description.isBlank()) {
      this.description = description;
    }
  }

}
