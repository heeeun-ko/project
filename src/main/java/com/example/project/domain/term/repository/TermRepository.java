package com.example.project.domain.term.repository;

import com.example.project.domain.term.entities.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {

  boolean existsByName(String name);

}
