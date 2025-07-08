package com.sparklet.controller;

import com.sparklet.dto.ApiResponse;
import com.sparklet.model.InterviewQuestion;
import com.sparklet.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/interview")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @GetMapping("/questions")
    public ResponseEntity<?> getAllQuestions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty) {
        
        List<InterviewQuestion> questions;
        
        if (type != null) {
            questions = interviewService.getQuestionsByType(type);
        } else if (category != null) {
            questions = interviewService.getQuestionsByCategory(category);
        } else if (difficulty != null) {
            questions = interviewService.getQuestionsByDifficulty(difficulty);
        } else {
            questions = interviewService.getAllQuestions();
        }
        
        return ResponseEntity.ok(ApiResponse.success(questions));
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable Long id) {
        InterviewQuestion question = interviewService.getQuestionById(id);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(question));
    }

    @PostMapping("/questions")
    public ResponseEntity<?> createQuestion(@RequestBody InterviewQuestion question) {
        InterviewQuestion created = interviewService.createQuestion(question);
        return ResponseEntity.ok(ApiResponse.success("Question created successfully", created));
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody InterviewQuestion question) {
        InterviewQuestion updated = interviewService.updateQuestion(id, question);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success("Question updated successfully", updated));
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        boolean deleted = interviewService.deleteQuestion(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success("Question deleted successfully"));
    }
}
