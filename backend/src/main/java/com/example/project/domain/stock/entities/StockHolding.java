package com.example.project.domain.stock.entities;

import com.example.project.global.entity.BaseEntity;
import com.example.project.global.enums.Status;
import com.example.project.global.exception.CustomException;
import com.example.project.global.exception.ErrorCodeEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// 현재 보유 포트폴리오

@Entity
@Table(name = "stock_holding",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "symbol"})
    })
@SuperBuilder
@NoArgsConstructor
@Getter
public class StockHolding extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

  @Column(nullable = false, length = 20)
  private String symbol; // 종목 코드

  @Column(nullable = false, length = 40)
  private String name; // 종목명

  @Column(nullable = false)
  private Long quantity; // 보유 수량

  @Column(nullable = false)
  private Long averagePrice; // 평균 매수가

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private Status status = Status.ACTIVE;

  public static StockHolding create(Account account, String symbol, String name, long quantity, long averagePrice) {
    return StockHolding.builder()
        .account(account)
        .symbol(symbol)
        .name(name)
        .quantity(quantity)
        .averagePrice(averagePrice)
        .build();
  }

  public void buy(long price, long quantity) {
    if (price <= 0 || quantity <= 0) {
      throw new IllegalArgumentException("매수 가격과 수량은 0보다 커야 합니다.");
    }

    long totalCost = this.averagePrice * this.quantity;
    long newCost = price * quantity;

    this.quantity += quantity;
    this.averagePrice = (totalCost + newCost) / this.quantity;
  }

  public void sell(long quantity) {
    if (quantity <= 0) {
      throw new CustomException(ErrorCodeEnum.STOCK_INVALID_QUANTITY);
    }
    if (this.quantity < quantity) {
      throw new CustomException(ErrorCodeEnum.STOCK_SELL_QUANTITY_EXCEEDED);
    }

    this.quantity -= quantity;
  }

  public void deactivate() {
    this.status = Status.INACTIVE;
  }

}