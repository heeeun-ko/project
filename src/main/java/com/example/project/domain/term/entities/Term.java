package com.example.project.domain.term.entities;

import com.example.project.domain.term.enums.TermLevel;
import com.example.project.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

}
