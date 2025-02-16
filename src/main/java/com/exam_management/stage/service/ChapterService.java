package com.exam_management.stage.service;

import com.exam_management.stage.entity.Chapter;
import com.exam_management.stage.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    public Chapter createChapter(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    public List<Chapter> getAllChapters() {
        return chapterRepository.findAll();
    }

    public Optional<Chapter> getChapterById(Long id) {
        return chapterRepository.findById(id);
    }

    public Chapter updateChapter(Long id, Chapter chapterDetails) {
        Chapter chapter = chapterRepository.findById(id).orElseThrow(() -> new RuntimeException("Chapter not found"));
        chapter.setName(chapterDetails.getName());
        return chapterRepository.save(chapter);
    }

    public void deleteChapter(Long id) {
        chapterRepository.deleteById(id);
    }
}