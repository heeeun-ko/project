package com.example.project.domain.stock.entities;

import com.example.project.domain.user.entities.User;
import com.example.project.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "stock")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Stock extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String symbol;  // 종목 코드

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Long quantity;

  @Column(nullable = false)
  private Long averagePrice; // 평균 매수가

  public Stock(User user, String symbol, String name, long quantity, long averagePrice) {
    this.user = user;
    this.symbol = symbol;
    this.name = name;
    this.quantity = quantity;
    this.averagePrice = averagePrice;
  }
}
