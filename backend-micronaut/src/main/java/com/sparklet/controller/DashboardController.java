
package com.sparklet.controller;

import com.sparklet.entity.Role;
import com.sparklet.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/api/dashboard")
@Secured("isAuthenticated()")
public class DashboardController {

    @Inject
    private UserService userService;

    @Get("/")
    public HttpResponse<Map<String, Object>> getUserDashboard(Authentication authentication) {
        Map<String, Object> dashboard = new HashMap<>();
        
        // Welcome section
        dashboard.put("welcomeMessage", "Welcome back, " + authentication.getName() + "!");
        dashboard.put("lastLogin", LocalDateTime.now().toString());
        dashboard.put("userRoles", authentication.getRoles());
        
        // Quick stats based on user role
        Map<String, Object> quickStats = new HashMap<>();
        if (authentication.getRoles().contains("ROLE_ADMIN")) {
            quickStats.put("totalUsers", userService.countUsers());
            quickStats.put("adminUsers", userService.countUsersByRole(Role.ROLE_ADMIN));
            quickStats.put("regularUsers", userService.countUsersByRole(Role.ROLE_USER));
            quickStats.put("userType", "Administrator");
        } else {
            quickStats.put("userType", "Standard User");
            quickStats.put("accountStatus", "Active");
            quickStats.put("memberSince", "Recently joined");
        }
        dashboard.put("quickStats", quickStats);
        
        // Recent activity (mock data for now)
        dashboard.put("recentActivity", List.of(
            Map.of("action", "Logged in successfully", "timestamp", LocalDateTime.now().minusMinutes(1)),
            Map.of("action", "Profile viewed", "timestamp", LocalDateTime.now().minusHours(2)),
            Map.of("action", "Security settings verified", "timestamp", LocalDateTime.now().minusHours(5))
        ));
        
        // Available features based on role
        List<String> availableFeatures;
        if (authentication.getRoles().contains("ROLE_ADMIN")) {
            availableFeatures = List.of(
                "User Management",
                "System Administration", 
                "Security Settings",
                "Analytics Dashboard",
                "User Reports"
            );
        } else {
            availableFeatures = List.of(
                "Profile Management",
                "Account Settings",
                "Activity History",
                "Help & Support"
            );
        }
        dashboard.put("availableFeatures", availableFeatures);
        
        return HttpResponse.ok(dashboard);
    }

    @Get("/stats")
    public HttpResponse<Map<String, Object>> getDashboardStats(Authentication authentication) {
        Map<String, Object> stats = new HashMap<>();
        
        if (authentication.getRoles().contains("ROLE_ADMIN")) {
            // Admin-level statistics
            stats.put("systemStats", Map.of(
                "totalUsers", userService.countUsers(),
                "adminUsers", userService.countUsersByRole(Role.ROLE_ADMIN),
                "regularUsers", userService.countUsersByRole(Role.ROLE_USER),
                "activeToday", userService.countUsers(), // Mock - all users for now
                "newThisWeek", Math.max(0, userService.countUsers() - 5) // Mock calculation
            ));
            
            stats.put("recentUsers", userService.getRecentUsers(3));
            
        } else {
            // Regular user statistics
            stats.put("personalStats", Map.of(
                "accountAge", "Active account",
                "lastActivity", LocalDateTime.now().toString(),
                "profileComplete", "100%",
                "securityLevel", "Standard"
            ));
        }
        
        return HttpResponse.ok(stats);
    }

    @Get("/notifications")
    public HttpResponse<Map<String, Object>> getNotifications(Authentication authentication) {
        Map<String, Object> notifications = new HashMap<>();
        
        // Mock notifications based on role
        if (authentication.getRoles().contains("ROLE_ADMIN")) {
            notifications.put("adminNotifications", List.of(
                Map.of(
                    "type", "system",
                    "message", "System running normally",
                    "priority", "low",
                    "timestamp", LocalDateTime.now().minusHours(1)
                ),
                Map.of(
                    "type", "user",
                    "message", "New user registrations: " + Math.max(0, userService.countUsers() - 5),
                    "priority", "medium", 
                    "timestamp", LocalDateTime.now().minusHours(3)
                )
            ));
        } else {
            notifications.put("userNotifications", List.of(
                Map.of(
                    "type", "welcome",
                    "message", "Welcome to Sparklet! Your account is now active.",
                    "priority", "low",
                    "timestamp", LocalDateTime.now().minusHours(2)
                ),
                Map.of(
                    "type", "security",
                    "message", "Your account security is up to date",
                    "priority", "low",
                    "timestamp", LocalDateTime.now().minusHours(6)
                )
            ));
        }
        
        return HttpResponse.ok(notifications);
    }

    @Get("/quick-actions")
    public HttpResponse<Map<String, Object>> getQuickActions(Authentication authentication) {
        Map<String, Object> actions = new HashMap<>();
        
        if (authentication.getRoles().contains("ROLE_ADMIN")) {
            actions.put("adminActions", List.of(
                Map.of("name", "View All Users", "endpoint", "/api/admin/users", "icon", "users"),
                Map.of("name", "System Stats", "endpoint", "/api/admin/stats", "icon", "chart"),
                Map.of("name", "Security Settings", "endpoint", "/api/admin/security", "icon", "shield"),
                Map.of("name", "User Reports", "endpoint", "/api/admin/reports", "icon", "report")
            ));
        } else {
            actions.put("userActions", List.of(
                Map.of("name", "View Profile", "endpoint", "/api/profile/me", "icon", "user"),
                Map.of("name", "Account Settings", "endpoint", "/api/profile/settings", "icon", "settings"),
                Map.of("name", "Activity History", "endpoint", "/api/profile/activity", "icon", "history"),
                Map.of("name", "Help & Support", "endpoint", "/api/support", "icon", "help")
            ));
        }
        
        return HttpResponse.ok(actions);
    }
}