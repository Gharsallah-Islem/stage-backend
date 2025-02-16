package com.exam_management.stage.repository;

import com.exam_management.stage.entity.ChapterRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRatingRepository extends JpaRepository<ChapterRating, Long> {
    List<ChapterRating> findByUserId(Long userId);
    List<ChapterRating> findByChapterId(Long chapterId);
}
