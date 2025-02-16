package com.exam_management.stage.controller;

import com.exam_management.stage.entity.ChapterRating;
import com.exam_management.stage.service.ChapterRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/chapter-ratings")
public class ChapterRatingController {

    @Autowired
    private ChapterRatingService chapterRatingService;

    @PostMapping
    public ResponseEntity<?> saveRating(@RequestBody ChapterRating rating) {
        try {
            ChapterRating savedRating = chapterRatingService.saveRating(rating);
            return ResponseEntity.ok(savedRating);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving rating: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<ChapterRating> getRatingsByUserId(@PathVariable Long userId) {
        return chapterRatingService.getRatingsByUserId(userId);
    }

    @GetMapping("/chapter/{chapterId}")
    public List<ChapterRating> getRatingsByChapterId(@PathVariable Long chapterId) {
        return chapterRatingService.getRatingsByChapterId(chapterId);
    }
}
