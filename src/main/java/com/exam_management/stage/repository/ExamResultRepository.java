package com.exam_management.stage.repository;

import com.exam_management.stage.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    List<ExamResult> findByUserId(Long userId);

    List<ExamResult> findByChapterId(Long chapterId);

    @Query("SELECT AVG(e.score) FROM ExamResult e WHERE e.user.id = :userId")
    Double findAverageScoreByUserId(Long userId);

    @Query("SELECT AVG(e.score) FROM ExamResult e WHERE e.chapter.id = :chapterId")
    Double findAverageScoreByChapterId(Long chapterId);

    @Query("SELECT AVG(e.score) FROM ExamResult e")
    Double getAverageScore();
}
