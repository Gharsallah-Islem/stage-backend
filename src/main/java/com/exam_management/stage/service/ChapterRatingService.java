package com.exam_management.stage.service;

import com.exam_management.stage.entity.ChapterRating;
import com.exam_management.stage.repository.ChapterRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterRatingService {

    @Autowired
    private ChapterRatingRepository chapterRatingRepository;

    public ChapterRating saveRating(ChapterRating rating) {
        return chapterRatingRepository.save(rating);
    }

    public List<ChapterRating> getRatingsByUserId(Long userId) {
        return chapterRatingRepository.findByUserId(userId);
    }

    public List<ChapterRating> getRatingsByChapterId(Long chapterId) {
        return chapterRatingRepository.findByChapterId(chapterId);
    }
}
