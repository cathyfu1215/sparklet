package com.sparklet.controller;

import com.sparklet.dto.ApiResponse;
import com.sparklet.model.Feedback;
import com.sparklet.security.UserPrincipal;
import com.sparklet.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/received")
    public ResponseEntity<?> getReceivedFeedback(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<Feedback> feedback = feedbackService.getFeedbackForUser(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success(feedback));
    }

    @GetMapping("/given")
    public ResponseEntity<?> getGivenFeedback(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<Feedback> feedback = feedbackService.getFeedbackFromUser(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success(feedback));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFeedback(@PathVariable Long id) {
        Feedback feedback = feedbackService.getFeedbackById(id);
        if (feedback == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(feedback));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<?> getFeedbackByType(@PathVariable String type) {
        List<Feedback> feedback = feedbackService.getFeedbackByType(type);
        return ResponseEntity.ok(ApiResponse.success(feedback));
    }

    @PostMapping
    public ResponseEntity<?> createFeedback(@RequestBody Feedback feedback, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        feedback.setFromUserId(userPrincipal.getId());
        Feedback created = feedbackService.createFeedback(feedback);
        return ResponseEntity.ok(ApiResponse.success("Feedback created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFeedback(@PathVariable Long id, @RequestBody Feedback feedback) {
        Feedback updated = feedbackService.updateFeedback(id, feedback);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success("Feedback updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id) {
        boolean deleted = feedbackService.deleteFeedback(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success("Feedback deleted successfully"));
    }
}
