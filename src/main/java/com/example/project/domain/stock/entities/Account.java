package com.example.project.domain.stock.entities;

import com.example.project.domain.user.entities.User;
import com.example.project.global.entity.BaseEntity;
import com.example.project.global.enums.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor
@SuperBuilder
public class Account extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false, length = 30)
  private String broker; // 증권사

  @Column(length = 50)
  private String nickname; // 계좌 별칭 (예: 삼성증권, 키움)

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private Status status = Status.ACTIVE;


  public void deactivate() {
    this.status = Status.INACTIVE;
  }

}