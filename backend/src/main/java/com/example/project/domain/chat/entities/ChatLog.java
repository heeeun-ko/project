package com.example.project.domain.chat.entities;

import com.example.project.domain.chat.enums.ChatRole;
import com.example.project.domain.user.entities.User;
import com.example.project.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "chat_log")
@SuperBuilder
@NoArgsConstructor
@Getter
public class ChatLog extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ChatRole role;  // USER / AI

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  public ChatLog(User user, ChatRole role, String content) {
    this.user = user;
    this.role = role;
    this.content = content;
  }
}
