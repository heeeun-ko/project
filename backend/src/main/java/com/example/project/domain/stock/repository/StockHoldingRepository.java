package com.example.project.domain.stock.repository;

import com.example.project.domain.stock.entities.StockHolding;
import com.example.project.global.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockHoldingRepository extends JpaRepository<StockHolding, Long> {

  Optional<StockHolding> findByAccountIdAndSymbolAndStatus(Long accountId, String symbol, Status status);

}
