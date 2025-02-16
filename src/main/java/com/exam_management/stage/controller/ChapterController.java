package com.exam_management.stage.controller;

import com.exam_management.stage.entity.Chapter;
import com.exam_management.stage.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chapters")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @PostMapping
    public Chapter createChapter(@RequestBody Chapter chapter) {
        return chapterService.createChapter(chapter);
    }

    @GetMapping
    public List<Chapter> getAllChapters() {
        return chapterService.getAllChapters();
    }

    @GetMapping("/{id}")
    public Optional<Chapter> getChapterById(@PathVariable Long id) {
        return chapterService.getChapterById(id);
    }

    @PutMapping("/{id}")
    public Chapter updateChapter(@PathVariable Long id, @RequestBody Chapter chapterDetails) {
        return chapterService.updateChapter(id, chapterDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteChapter(@PathVariable Long id) {
        chapterService.deleteChapter(id);
    }
}