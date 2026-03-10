package com.example.project.domain.stock.dto.response;

import com.example.project.domain.stock.entities.StockTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class StockTransactionResponseDto {

  private String symbol;
  private String name;
  private String type;
  private Long price;
  private Long quantity;
  private LocalDate transactionDate;

  public static StockTransactionResponseDto from(StockTransaction stockTransaction) {
    return new StockTransactionResponseDto(
        stockTransaction.getSymbol(),
        stockTransaction.getName(),
        stockTransaction.getType().name(),
        stockTransaction.getPrice(),
        stockTransaction.getQuantity(),
        stockTransaction.getTransactionDate()
    );
  }
}
