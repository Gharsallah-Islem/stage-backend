package com.exam_management.stage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chapter_ratings")
@Data
public class ChapterRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @Column(nullable = false)
    private int rating;
}
