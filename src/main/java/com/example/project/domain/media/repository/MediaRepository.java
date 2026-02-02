package com.example.project.domain.media.repository;

import com.example.project.domain.media.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

  boolean existsByName(String name);

}
