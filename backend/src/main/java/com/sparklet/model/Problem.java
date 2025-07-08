package com.sparklet.model;

import java.time.LocalDateTime;

public class Problem {
    private Long id;
    private String title;
    private String description;
    private String difficulty; // EASY, MEDIUM, HARD
    private String category; // ALGORITHMS, DATA_STRUCTURES, SYSTEM_DESIGN, etc.
    private String solution;
    private String hints;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public Problem() {}

    public Problem(Long id, String title, String description, String difficulty, String category, String solution, String hints) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.category = category;
        this.solution = solution;
        this.hints = hints;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSolution() { return solution; }
    public void setSolution(String solution) { this.solution = solution; }

    public String getHints() { return hints; }
    public void setHints(String hints) { this.hints = hints; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
