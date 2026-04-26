package com.example.project.domain.stock.repository;

import com.example.project.domain.stock.entities.StockHolding;
import com.example.project.domain.stock.entities.StockTransaction;
import com.example.project.domain.stock.enums.StockTransactionType;
import com.example.project.global.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
      AND (:type IS NULL OR st.type = :type)
      AND (:broker IS NULL OR st.account.broker = :broker)
      AND (:nickname IS NULL OR st.account.nickname = :nickname)
      AND (:fromDate IS NULL OR st.transactionDate >= :fromDate)
      AND (:toDate IS NULL OR st.transactionDate <= :toDate)
      ORDER BY st.transactionDate DESC
      """)
  List<StockTransaction> findTransactions(
      @Param("userId") Long userId,
      @Param("accountId") Long accountId,
      @Param("symbol") String symbol,
      @Param("type") StockTransactionType type,
      @Param("broker") String broker,
      @Param("nickname") String nickname,
      @Param("fromDate") LocalDate fromDate,
      @Param("toDate") LocalDate toDate
  );

  @Query("""
      SELECT sh
      FROM StockHolding sh
      WHERE sh.status = 'ACTIVE'
      AND sh.account.user.id = :userId
      AND (:accountId IS NULL OR sh.account.id = :accountId)
      AND (:symbol IS NULL OR sh.symbol = :symbol)
      AND (:broker IS NULL OR sh.account.broker = :broker)
      AND (:nickname IS NULL OR sh.account.nickname = :nickname)
      AND (:fromDate IS NULL OR sh.createdAt >= :fromDate)
      AND (:toDate IS NULL OR sh.createdAt <= :toDate)
      ORDER BY sh.createdAt DESC
      """)
  List<StockHolding> findHoldings(
      @Param("userId") Long userId,
      @Param("accountId") Long accountId,
      @Param("symbol") String symbol,
      @Param("broker") String broker,
      @Param("nickname") String nickname,
      @Param("fromDate") LocalDate fromDate,
      @Param("toDate") LocalDate toDate
  );

}
