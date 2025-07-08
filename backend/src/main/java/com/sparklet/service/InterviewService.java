package com.sparklet.service;

import com.sparklet.model.InterviewQuestion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class InterviewService {
    
    private final Map<Long, InterviewQuestion> questions = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public InterviewService() {
        // Initialize with mock data
        createMockQuestion("Tell me about yourself", "BEHAVIORAL", "EASY", "GENERAL", "Structure your answer using past, present, future format.", "Keep it concise and relevant to the role.");
        createMockQuestion("What's your greatest weakness?", "BEHAVIORAL", "MEDIUM", "GENERAL", "Choose a real weakness but show how you're working to improve it.", "Don't say 'I'm a perfectionist'.");
        createMockQuestion("Explain the difference between == and === in JavaScript", "TECHNICAL", "EASY", "FRONTEND", "== does type coercion, === does not. Always use === for strict equality.", "Think about type safety.");
        createMockQuestion("How would you design a URL shortener like bit.ly?", "SYSTEM_DESIGN", "HARD", "BACKEND", "Consider database design, encoding algorithms, caching, and scaling.", "Start with basic requirements, then scale.");
        createMockQuestion("Describe a time when you had to work with a difficult team member", "BEHAVIORAL", "MEDIUM", "TEAMWORK", "Use the STAR method (Situation, Task, Action, Result).", "Focus on your communication and problem-solving skills.");
        createMockQuestion("Implement a function to reverse a string", "TECHNICAL", "EASY", "ALGORITHMS", "Multiple approaches: array reversal, recursion, or built-in methods.", "Consider time and space complexity.");
        createMockQuestion("What happens when you type a URL in a browser?", "TECHNICAL", "MEDIUM", "GENERAL", "DNS lookup, TCP connection, HTTP request, server processing, response, rendering.", "Cover the full stack from network to rendering.");
        createMockQuestion("Design a chat application", "SYSTEM_DESIGN", "HARD", "GENERAL", "WebSockets, message queues, database design, scaling strategies.", "Think about real-time requirements.");
    }

    private void createMockQuestion(String question, String type, String difficulty, String category, String suggestedAnswer, String tips) {
        InterviewQuestion interviewQuestion = new InterviewQuestion(
                idCounter.getAndIncrement(),
                question,
                type,
                difficulty,
                category,
                suggestedAnswer,
                tips
        );
        questions.put(interviewQuestion.getId(), interviewQuestion);
    }

    public List<InterviewQuestion> getAllQuestions() {
        return questions.values().stream().collect(Collectors.toList());
    }

    public InterviewQuestion getQuestionById(Long id) {
        return questions.get(id);
    }

    public List<InterviewQuestion> getQuestionsByType(String type) {
        return questions.values().stream()
                .filter(question -> question.getType().equals(type))
                .collect(Collectors.toList());
    }

    public List<InterviewQuestion> getQuestionsByCategory(String category) {
        return questions.values().stream()
                .filter(question -> question.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public List<InterviewQuestion> getQuestionsByDifficulty(String difficulty) {
        return questions.values().stream()
                .filter(question -> question.getDifficulty().equals(difficulty))
                .collect(Collectors.toList());
    }

    public InterviewQuestion createQuestion(InterviewQuestion question) {
        question.setId(idCounter.getAndIncrement());
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());
        questions.put(question.getId(), question);
        return question;
    }

    public InterviewQuestion updateQuestion(Long id, InterviewQuestion question) {
        InterviewQuestion existing = questions.get(id);
        if (existing != null) {
            question.setId(id);
            question.setCreatedAt(existing.getCreatedAt());
            question.setUpdatedAt(LocalDateTime.now());
            questions.put(id, question);
            return question;
        }
        return null;
    }

    public boolean deleteQuestion(Long id) {
        return questions.remove(id) != null;
    }
}
