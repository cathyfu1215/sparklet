package com.sparklet.controller;

import com.sparklet.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping({"", "/"})
    public ResponseEntity<?> welcome() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "Sparklet API");
        info.put("version", "1.0.0");
        info.put("description", "Backend API for Sparklet career development platform");
        info.put("status", "Running");
        
        Map<String, Object> endpoints = new HashMap<>();
        endpoints.put("auth", "/api/auth/signin, /api/auth/signup");
        endpoints.put("dashboard", "/api/dashboard/stats");
        endpoints.put("applications", "/api/applications");
        endpoints.put("problems", "/api/problems");
        endpoints.put("interview", "/api/interview/questions");
        endpoints.put("feedback", "/api/feedback");
        endpoints.put("user", "/api/user/profile");
        endpoints.put("pages", "/api/pages");
        endpoints.put("test", "/api/test/public, /api/test/user");
        
        info.put("endpoints", endpoints);
        
        Map<String, String> sampleCredentials = new HashMap<>();
        sampleCredentials.put("email", "tom@example.com");
        sampleCredentials.put("password", "password123");
        info.put("sampleCredentials", sampleCredentials);
        
        return ResponseEntity.ok(ApiResponse.success("Welcome to Sparklet API", info));
    }
}
