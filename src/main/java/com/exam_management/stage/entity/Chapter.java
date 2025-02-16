package com.exam_management.stage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "chapters")
@Data
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ChapterRating> ratings;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ExamResult> examResults;
}
