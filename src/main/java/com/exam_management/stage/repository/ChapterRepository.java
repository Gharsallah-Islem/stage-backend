package com.exam_management.stage.repository;

import com.exam_management.stage.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query("SELECT c.name AS chapterName, AVG(e.score) AS avgScore, AVG(r.rating) AS avgRating " +
            "FROM Chapter c " +
            "LEFT JOIN ExamResult e ON e.chapter.id = c.id " +
            "LEFT JOIN ChapterRating r ON r.chapter.id = c.id " +
            "GROUP BY c.id, c.name")
    List<Map<String, Object>> getTrainingNeedsMatrix();
}
