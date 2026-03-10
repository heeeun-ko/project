package com.example.project.domain.stock.repository;

import com.example.project.domain.stock.entities.StockTransaction;
import com.example.project.global.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

  @Query("""
      SELECT st
      FROM StockTransaction st
      WHERE st.status = 'ACTIVE'
      AND st.account.user.id = :userId
      AND (:accountId IS NULL OR st.account.id = :accountId)
      AND (:symbol IS NULL OR st.symbol = :symbol)
      ORDER BY st.transactionDate DESC
      """)
  List<StockTransaction> findTransactions(
      @Param("userId") Long userId,
      @Param("accountId") Long accountId,
      @Param("symbol") String symbol
  );

}
