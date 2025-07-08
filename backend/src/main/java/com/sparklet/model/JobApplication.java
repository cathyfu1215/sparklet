package com.sparklet.model;

import java.time.LocalDateTime;

public class JobApplication {
    private Long id;
    private Long userId;
    private String company;
    private String position;
    private String status; // APPLIED, INTERVIEW, OFFER, REJECTED, ACCEPTED
    private String jobUrl;
    private String notes;
    private LocalDateTime appliedDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public JobApplication() {}

    public JobApplication(Long id, Long userId, String company, String position, String status, String jobUrl, String notes, LocalDateTime appliedDate) {
        this.id = id;
        this.userId = userId;
        this.company = company;
        this.position = position;
        this.status = status;
        this.jobUrl = jobUrl;
        this.notes = notes;
        this.appliedDate = appliedDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getJobUrl() { return jobUrl; }
    public void setJobUrl(String jobUrl) { this.jobUrl = jobUrl; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDateTime appliedDate) { this.appliedDate = appliedDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
