package com.example.project.domain.user.entities;

import com.example.project.domain.user.enums.AuthProvider;
import com.example.project.domain.user.enums.UserRole;
import com.example.project.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "unique_email_provider",
            columnNames = {"email", "auth_provider"}
        )
    }
)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User extends BaseEntity {

  @Column(length = 100, nullable = false)
  private String email;

  @Column(length = 50, nullable = false)
  private String name;

  @Column(name = "profile_image_url")
  private String profileImageUrl;

  @Enumerated(EnumType.STRING)
  @Column(name = "auth_provider", nullable = false)
  private AuthProvider authProvider; // GOOGLE / NAVER / KAKAO

  @Enumerated(EnumType.STRING)
  @Column(name = "user_role", nullable = false)
  private UserRole userRole; // USER / ADMIN


  public void updateProfile(String name, String profileImageUrl) {

    if (name != null && !name.isBlank()) {
      this.name = name;
    }

    if (profileImageUrl != null && !profileImageUrl.isBlank()) {
      this.profileImageUrl = profileImageUrl;
    }
  }

}
