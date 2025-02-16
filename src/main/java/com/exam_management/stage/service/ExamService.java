package com.exam_management.stage.service;

import com.exam_management.stage.entity.ExamResult;
import com.exam_management.stage.entity.User;
import com.exam_management.stage.entity.Chapter;
import com.exam_management.stage.repository.ExamResultRepository;
import com.exam_management.stage.repository.UserRepository;
import com.exam_management.stage.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    @Autowired
    private ExamResultRepository examResultRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChapterRepository chapterRepository;  

    public double calculateAverage(List<Double> scores) {
        return scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public List<ExamResult> getResultsByUserId(Long userId) {
        return examResultRepository.findByUserId(userId);
    }

    public List<ExamResult> getResultsByChapterId(Long chapterId) {
        return examResultRepository.findByChapterId(chapterId);
    }

    public void saveExamResults(List<ExamResult> results) {
        for (ExamResult result : results) {
            User user = userRepository.findById(result.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + result.getUser().getId()));

            Chapter chapter = chapterRepository.findById(result.getChapter().getId())
                    .orElseThrow(() -> new RuntimeException("Chapter not found with ID: " + result.getChapter().getId()));

            result.setUser(user);
            result.setChapter(chapter);

            System.out.println("Saving ExamResult -> User: " + user.getId() + ", Chapter: " + chapter.getId() + ", Score: " + result.getScore());

            examResultRepository.save(result);
        }
    }

}
