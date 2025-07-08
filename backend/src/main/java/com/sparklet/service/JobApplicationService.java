package com.sparklet.service;

import com.sparklet.model.JobApplication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {
    
    private final Map<Long, JobApplication> applications = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public JobApplicationService() {
        // Initialize with mock data
        createMockApplication(1L, "Google", "Software Engineer", "INTERVIEW", "https://careers.google.com", "Phone screen completed");
        createMockApplication(1L, "Microsoft", "Frontend Developer", "APPLIED", "https://careers.microsoft.com", "Applied last week");
        createMockApplication(1L, "Apple", "iOS Developer", "REJECTED", "https://jobs.apple.com", "Not a good fit");
        createMockApplication(1L, "Meta", "Full Stack Engineer", "OFFER", "https://careers.meta.com", "Received offer!");
        createMockApplication(1L, "Netflix", "Backend Engineer", "APPLIED", "https://jobs.netflix.com", "Waiting for response");
    }

    private void createMockApplication(Long userId, String company, String position, String status, String jobUrl, String notes) {
        JobApplication app = new JobApplication(
                idCounter.getAndIncrement(),
                userId,
                company,
                position,
                status,
                jobUrl,
                notes,
                LocalDateTime.now().minusDays((long) (Math.random() * 30))
        );
        applications.put(app.getId(), app);
    }

    public List<JobApplication> getApplicationsByUserId(Long userId) {
        return applications.values().stream()
                .filter(app -> app.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public JobApplication getApplicationById(Long id) {
        return applications.get(id);
    }

    public JobApplication createApplication(JobApplication application) {
        application.setId(idCounter.getAndIncrement());
        application.setCreatedAt(LocalDateTime.now());
        application.setUpdatedAt(LocalDateTime.now());
        applications.put(application.getId(), application);
        return application;
    }

    public JobApplication updateApplication(Long id, JobApplication application) {
        JobApplication existing = applications.get(id);
        if (existing != null) {
            application.setId(id);
            application.setCreatedAt(existing.getCreatedAt());
            application.setUpdatedAt(LocalDateTime.now());
            applications.put(id, application);
            return application;
        }
        return null;
    }

    public boolean deleteApplication(Long id) {
        return applications.remove(id) != null;
    }
}
