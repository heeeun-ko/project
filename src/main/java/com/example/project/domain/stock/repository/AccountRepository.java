package com.example.project.domain.stock.repository;

import com.example.project.domain.stock.entities.Account;
import com.example.project.global.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  List<Account> findByUserIdAndStatus(Long userId, Status status);

  Optional<Account> findByIdAndStatus(Long accountId, Status status);

}
