package com.sparklet.service;

import com.sparklet.model.Feedback;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class FeedbackService {
    
    private final Map<Long, Feedback> feedbacks = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public FeedbackService() {
        // Initialize with mock data
        createMockFeedback(2L, 1L, "PEER_REVIEW", "Code Review for Login Feature", "Great implementation of the authentication system. The code is clean and well-structured. Consider adding more error handling for edge cases.", 4, "COMPLETED");
        createMockFeedback(3L, 1L, "MOCK_INTERVIEW", "Mock Technical Interview", "Solid performance on the technical questions. Algorithm approach was correct but could be optimized. Communication skills are excellent.", 4, "COMPLETED");
        createMockFeedback(2L, 1L, "CODE_REVIEW", "React Component Review", "Good use of hooks and component structure. Suggest extracting some logic into custom hooks for better reusability.", 4, "COMPLETED");
        createMockFeedback(1L, 2L, "PEER_REVIEW", "System Design Feedback", "Interesting approach to the scaling problem. The database design is solid, but consider adding more caching layers.", 5, "COMPLETED");
        createMockFeedback(3L, 1L, "MOCK_INTERVIEW", "Behavioral Interview Practice", "Well-prepared answers using the STAR method. Could provide more specific examples from past projects.", 4, "PENDING");
    }

    private void createMockFeedback(Long fromUserId, Long toUserId, String type, String title, String content, Integer rating, String status) {
        Feedback feedback = new Feedback(
                idCounter.getAndIncrement(),
                fromUserId,
                toUserId,
                type,
                title,
                content,
                rating,
                status
        );
        feedbacks.put(feedback.getId(), feedback);
    }

    public List<Feedback> getFeedbackForUser(Long userId) {
        return feedbacks.values().stream()
                .filter(feedback -> feedback.getToUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Feedback> getFeedbackFromUser(Long userId) {
        return feedbacks.values().stream()
                .filter(feedback -> feedback.getFromUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Feedback getFeedbackById(Long id) {
        return feedbacks.get(id);
    }

    public List<Feedback> getFeedbackByType(String type) {
        return feedbacks.values().stream()
                .filter(feedback -> feedback.getType().equals(type))
                .collect(Collectors.toList());
    }

    public Feedback createFeedback(Feedback feedback) {
        feedback.setId(idCounter.getAndIncrement());
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setUpdatedAt(LocalDateTime.now());
        feedbacks.put(feedback.getId(), feedback);
        return feedback;
    }

    public Feedback updateFeedback(Long id, Feedback feedback) {
        Feedback existing = feedbacks.get(id);
        if (existing != null) {
            feedback.setId(id);
            feedback.setCreatedAt(existing.getCreatedAt());
            feedback.setUpdatedAt(LocalDateTime.now());
            feedbacks.put(id, feedback);
            return feedback;
        }
        return null;
    }

    public boolean deleteFeedback(Long id) {
        return feedbacks.remove(id) != null;
    }
}
