package com.sparklet.model;

import java.time.LocalDateTime;

public class InterviewQuestion {
    private Long id;
    private String question;
    private String type; // BEHAVIORAL, TECHNICAL, SYSTEM_DESIGN
    private String difficulty; // EASY, MEDIUM, HARD
    private String category;
    private String suggestedAnswer;
    private String tips;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public InterviewQuestion() {}

    public InterviewQuestion(Long id, String question, String type, String difficulty, String category, String suggestedAnswer, String tips) {
        this.id = id;
        this.question = question;
        this.type = type;
        this.difficulty = difficulty;
        this.category = category;
        this.suggestedAnswer = suggestedAnswer;
        this.tips = tips;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSuggestedAnswer() { return suggestedAnswer; }
    public void setSuggestedAnswer(String suggestedAnswer) { this.suggestedAnswer = suggestedAnswer; }

    public String getTips() { return tips; }
    public void setTips(String tips) { this.tips = tips; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
