package com.example.project.domain.media.entities;

import com.example.project.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "media")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Media extends BaseEntity {

  @Column(nullable = false, length = 100, unique = true)
  private String name;

  @Column(name = "logo_url")
  private String logoUrl;
}
