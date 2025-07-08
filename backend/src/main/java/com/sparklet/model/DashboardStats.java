package com.sparklet.model;

import java.time.LocalDateTime;

public class DashboardStats {
    private Long totalApplications;
    private Long activeApplications;
    private Long interviewsScheduled;
    private Long problemsSolved;
    private Long feedbackReceived;
    private Double averageRating;
    private LocalDateTime lastUpdated;

    // Constructors
    public DashboardStats() {}

    public DashboardStats(Long totalApplications, Long activeApplications, Long interviewsScheduled, 
                         Long problemsSolved, Long feedbackReceived, Double averageRating) {
        this.totalApplications = totalApplications;
        this.activeApplications = activeApplications;
        this.interviewsScheduled = interviewsScheduled;
        this.problemsSolved = problemsSolved;
        this.feedbackReceived = feedbackReceived;
        this.averageRating = averageRating;
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getTotalApplications() { return totalApplications; }
    public void setTotalApplications(Long totalApplications) { this.totalApplications = totalApplications; }

    public Long getActiveApplications() { return activeApplications; }
    public void setActiveApplications(Long activeApplications) { this.activeApplications = activeApplications; }

    public Long getInterviewsScheduled() { return interviewsScheduled; }
    public void setInterviewsScheduled(Long interviewsScheduled) { this.interviewsScheduled = interviewsScheduled; }

    public Long getProblemsSolved() { return problemsSolved; }
    public void setProblemsSolved(Long problemsSolved) { this.problemsSolved = problemsSolved; }

    public Long getFeedbackReceived() { return feedbackReceived; }
    public void setFeedbackReceived(Long feedbackReceived) { this.feedbackReceived = feedbackReceived; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
