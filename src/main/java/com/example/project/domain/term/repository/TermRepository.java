package com.example.project.domain.term.repository;

import com.example.project.domain.term.entities.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TermRepository extends JpaRepository<Term, Long> {

  boolean existsByName(String name);

  Optional<Term> findByName(String name);
}
