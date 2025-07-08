package com.sparklet.service;

import com.sparklet.model.DashboardStats;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    public DashboardStats getDashboardStats(Long userId) {
        // Mock data for dashboard statistics
        return new DashboardStats(
                25L,  // totalApplications
                8L,   // activeApplications
                3L,   // interviewsScheduled
                47L,  // problemsSolved
                12L,  // feedbackReceived
                4.2   // averageRating
        );
    }
}
