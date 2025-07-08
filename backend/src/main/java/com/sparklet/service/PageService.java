package com.sparklet.service;

import com.sparklet.model.PageInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PageService {
    
    private final Map<String, PageInfo> pageInfoMap = new HashMap<>();

    public PageService() {
        initializePageInfo();
    }

    private void initializePageInfo() {
        pageInfoMap.put("application-tracker", new PageInfo(
                "Application Tracker",
                "Track your job applications, monitor their status, and manage your career opportunities. Keep tabs on interviews, offers, and follow-ups all in one place.",
                "DEVELOPMENT",
                "This feature is currently in development. Soon you'll be able to add, edit, and track all your job applications with detailed analytics."
        ));

        pageInfoMap.put("problem-solving", new PageInfo(
                "Problem Solving Practice",
                "Sharpen your coding skills with curated programming challenges. Practice algorithms, data structures, and system design problems to ace your technical interviews.",
                "DEVELOPMENT",
                "We're building an extensive library of coding problems with solutions, hints, and difficulty levels. Get ready to level up your programming skills!"
        ));

        pageInfoMap.put("interview-prep", new PageInfo(
                "Interview Preparation",
                "Prepare for your dream job with our comprehensive interview preparation tools. Practice behavioral questions, technical challenges, and system design scenarios.",
                "DEVELOPMENT",
                "Coming soon: Mock interviews, question banks categorized by company and role, AI-powered feedback, and personalized preparation plans."
        ));

        pageInfoMap.put("feedback-portal", new PageInfo(
                "Feedback Portal",
                "Give and receive constructive feedback from peers and mentors. Improve your skills through code reviews, mock interviews, and peer evaluations.",
                "DEVELOPMENT",
                "The feedback system is being designed to connect you with mentors and peers for valuable career insights and skill development opportunities."
        ));
    }

    public PageInfo getPageInfo(String pageId) {
        return pageInfoMap.get(pageId);
    }

    public Map<String, PageInfo> getAllPageInfo() {
        return new HashMap<>(pageInfoMap);
    }
}
