package com.example.project.domain.media.repository;

import com.example.project.domain.media.entities.MediaPick;
import com.example.project.domain.media.entities.MediaPickId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MediaPickRepository extends JpaRepository<MediaPick, MediaPickId> {

  List<MediaPick> findAllByUserId(Long userId);

  boolean existsByUserIdAndMediaId(Long userId, Long mediaId);

}