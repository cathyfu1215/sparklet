package com.sparklet.controller;

import com.sparklet.dto.ApiResponse;
import com.sparklet.model.JobApplication;
import com.sparklet.security.UserPrincipal;
import com.sparklet.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @GetMapping
    public ResponseEntity<?> getUserApplications(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<JobApplication> applications = jobApplicationService.getApplicationsByUserId(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success(applications));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplication(@PathVariable Long id) {
        JobApplication application = jobApplicationService.getApplicationById(id);
        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(application));
    }

    @PostMapping
    public ResponseEntity<?> createApplication(@RequestBody JobApplication application, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        application.setUserId(userPrincipal.getId());
        JobApplication created = jobApplicationService.createApplication(application);
        return ResponseEntity.ok(ApiResponse.success("Application created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateApplication(@PathVariable Long id, @RequestBody JobApplication application, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        application.setUserId(userPrincipal.getId());
        JobApplication updated = jobApplicationService.updateApplication(id, application);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success("Application updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplication(@PathVariable Long id) {
        boolean deleted = jobApplicationService.deleteApplication(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success("Application deleted successfully"));
    }
}
