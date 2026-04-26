package com.example.project.domain.stock.dto.response;

import com.example.project.domain.stock.entities.StockHolding;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockHoldingResponseDto {

  private Long accountId;
  private String broker;
  private String nickname;
  private String symbol;
  private String name;
  private Long quantity;
  private Long averagePrice;

  public static StockHoldingResponseDto from(StockHolding stockHolding) {
    return new StockHoldingResponseDto(
        stockHolding.getAccount().getId(),
        stockHolding.getAccount().getBroker(),
        stockHolding.getAccount().getNickname(),
        stockHolding.getSymbol(),
        stockHolding.getName(),
        stockHolding.getQuantity(),
        stockHolding.getAveragePrice()
    );
  }
}