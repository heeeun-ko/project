package com.example.project.domain.stock.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StockBuyRequestDto {

  private Long accountId;
  private String symbol;
  private String name;
  private Long price;
  private Long quantity;
  private LocalDate transactionDate;

}