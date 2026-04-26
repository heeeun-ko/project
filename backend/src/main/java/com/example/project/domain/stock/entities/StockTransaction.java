package com.example.project.domain.stock.entities;

import com.example.project.domain.stock.enums.StockTransactionType;
import com.example.project.global.entity.BaseEntity;
import com.example.project.global.enums.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

// 거래 기록

@Entity
@Table(name = "stock_transaction")
@SuperBuilder
@NoArgsConstructor
@Getter
public class StockTransaction extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

  @Column(nullable = false)
  private String symbol;

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StockTransactionType type; // BUY / SELL

  @Column(nullable = false)
  private Long price; // 거래 가격

  @Column(nullable = false)
  private Long quantity; // 거래 수량

  @Column(nullable = false)
  private LocalDate transactionDate; // 거래 날짜

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private Status status = Status.ACTIVE;


  public static StockTransaction create(
      Account account,
      String symbol,
      String name,
      StockTransactionType type,
      long price,
      long quantity,
      LocalDate transactionDate
  ) {
    return StockTransaction.builder()
        .account(account)
        .symbol(symbol)
        .name(name)
        .type(type)
        .price(price)
        .quantity(quantity)
        .transactionDate(transactionDate)
        .build();
  }

  public void deactivate() {
    this.status = Status.INACTIVE;
  }

}
