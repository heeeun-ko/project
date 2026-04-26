package com.example.project.domain.term.repository;

import com.example.project.domain.term.entities.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TermRepository extends JpaRepository<Term, Long> {

  boolean existsByName(String name);

  @Query(value = """
      SELECT * FROM term
      WHERE level = :level
      ORDER BY RANDOM()
      LIMIT :limit
      """, nativeQuery = true)
  List<Term> findRandomByLevel(
      @Param("level") String level,
      @Param("limit") int limit
  );

}
