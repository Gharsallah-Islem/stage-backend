package com.exam_management.stage.controller;

import com.exam_management.stage.entity.ExamResult;
import com.exam_management.stage.entity.User;
import com.exam_management.stage.service.ExamService;
import com.exam_management.stage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private static final Logger logger = LoggerFactory.getLogger(ExamController.class);

    @Autowired
    private ExamService examService;

    @Autowired
    private UserService userService;

    @PostMapping("/calculate")
    public double calculateAverage(@RequestBody List<Double> scores) {
        return examService.calculateAverage(scores);
    }

    @GetMapping("/results/{userId}")
    public List<ExamResult> getResultsByUserId(@PathVariable Long userId) {
        return examService.getResultsByUserId(userId);
    }

    @GetMapping("/results/chapter/{chapterId}")
    public List<ExamResult> getResultsByChapterId(@PathVariable Long chapterId) {
        return examService.getResultsByChapterId(chapterId);
    }

    @PostMapping("/submit-results")
    public ResponseEntity<Map<String, String>> submitResults(@RequestBody List<ExamResult> results) {
        Map<String, String> response = new HashMap<>();
        try {
            logger.info("Received exam results: {}", results);

            for (ExamResult result : results) {
                logger.info("Processing result: Score={}, User={}, Chapter={}",
                        result.getScore(),
                        result.getUser(),
                        result.getChapter());

                if (result.getUser() == null || result.getUser().getId() == null) {
                    response.put("error", "User ID is missing in request");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                User user = userService.getUserById(result.getUser().getId());
                if (user == null) {
                    response.put("error", "User not found with ID: " + result.getUser().getId());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                result.setUser(user);
            }

            examService.saveExamResults(results);
            response.put("message", "Exam results saved successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error saving exam results", e);
            response.put("error", "Error saving exam results: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
