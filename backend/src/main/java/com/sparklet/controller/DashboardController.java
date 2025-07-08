package com.sparklet.controller;

import com.sparklet.dto.ApiResponse;
import com.sparklet.model.DashboardStats;
import com.sparklet.security.UserPrincipal;
import com.sparklet.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<?> getDashboardStats(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        DashboardStats stats = dashboardService.getDashboardStats(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
}
