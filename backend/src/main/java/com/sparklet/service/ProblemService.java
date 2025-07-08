package com.sparklet.service;

import com.sparklet.model.Problem;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ProblemService {
    
    private final Map<Long, Problem> problems = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public ProblemService() {
        // Initialize with mock data
        createMockProblem("Two Sum", "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.", "EASY", "ALGORITHMS", "Use a hash map to store numbers and their indices.", "Think about what you've seen before.");
        createMockProblem("Merge Sort", "Implement merge sort algorithm to sort an array of integers.", "MEDIUM", "ALGORITHMS", "Divide the array into two halves, recursively sort them, then merge.", "Use divide and conquer approach.");
        createMockProblem("Binary Tree Traversal", "Implement in-order, pre-order, and post-order traversal of a binary tree.", "MEDIUM", "DATA_STRUCTURES", "Use recursion or stack-based iterative approach.", "Think about the order you visit nodes.");
        createMockProblem("System Design: Chat Application", "Design a real-time chat application that can handle millions of users.", "HARD", "SYSTEM_DESIGN", "Consider WebSocket connections, message queues, database sharding.", "Start with basic features, then scale.");
        createMockProblem("LRU Cache", "Design and implement a data structure for Least Recently Used (LRU) cache.", "MEDIUM", "DATA_STRUCTURES", "Use HashMap and Doubly Linked List combination.", "Think about O(1) operations.");
    }

    private void createMockProblem(String title, String description, String difficulty, String category, String solution, String hints) {
        Problem problem = new Problem(
                idCounter.getAndIncrement(),
                title,
                description,
                difficulty,
                category,
                solution,
                hints
        );
        problems.put(problem.getId(), problem);
    }

    public List<Problem> getAllProblems() {
        return problems.values().stream().collect(Collectors.toList());
    }

    public Problem getProblemById(Long id) {
        return problems.get(id);
    }

    public List<Problem> getProblemsByCategory(String category) {
        return problems.values().stream()
                .filter(problem -> problem.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public List<Problem> getProblemsByDifficulty(String difficulty) {
        return problems.values().stream()
                .filter(problem -> problem.getDifficulty().equals(difficulty))
                .collect(Collectors.toList());
    }

    public Problem createProblem(Problem problem) {
        problem.setId(idCounter.getAndIncrement());
        problem.setCreatedAt(LocalDateTime.now());
        problem.setUpdatedAt(LocalDateTime.now());
        problems.put(problem.getId(), problem);
        return problem;
    }

    public Problem updateProblem(Long id, Problem problem) {
        Problem existing = problems.get(id);
        if (existing != null) {
            problem.setId(id);
            problem.setCreatedAt(existing.getCreatedAt());
            problem.setUpdatedAt(LocalDateTime.now());
            problems.put(id, problem);
            return problem;
        }
        return null;
    }

    public boolean deleteProblem(Long id) {
        return problems.remove(id) != null;
    }
}
