package com.sparklet.controller;

import com.sparklet.dto.ApiResponse;
import com.sparklet.model.Problem;
import com.sparklet.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping
    public ResponseEntity<?> getAllProblems(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty) {
        
        List<Problem> problems;
        
        if (category != null) {
            problems = problemService.getProblemsByCategory(category);
        } else if (difficulty != null) {
            problems = problemService.getProblemsByDifficulty(difficulty);
        } else {
            problems = problemService.getAllProblems();
        }
        
        return ResponseEntity.ok(ApiResponse.success(problems));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProblem(@PathVariable Long id) {
        Problem problem = problemService.getProblemById(id);
        if (problem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(problem));
    }

    @PostMapping
    public ResponseEntity<?> createProblem(@RequestBody Problem problem) {
        Problem created = problemService.createProblem(problem);
        return ResponseEntity.ok(ApiResponse.success("Problem created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProblem(@PathVariable Long id, @RequestBody Problem problem) {
        Problem updated = problemService.updateProblem(id, problem);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success("Problem updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProblem(@PathVariable Long id) {
        boolean deleted = problemService.deleteProblem(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success("Problem deleted successfully"));
    }
}
