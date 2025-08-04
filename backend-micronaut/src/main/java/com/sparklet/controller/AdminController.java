package com.sparklet.controller;

import com.sparklet.entity.Role;
import com.sparklet.entity.User;
import com.sparklet.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/api/admin")
@Secured("ROLE_ADMIN")
public class AdminController {

    @Inject
    private UserService userService;

    @Get("/dashboard")
    public HttpResponse<Map<String, Object>> getAdminDashboard(Authentication authentication) {
        
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("message", "Admin Dashboard");
        dashboard.put("adminUser", authentication.getName());
        dashboard.put("totalUsers", userService.countUsers());
        dashboard.put("features", List.of(
            "User Management",
            "System Statistics", 
            "Security Settings",
            "Activity Logs"
        ));
        
        return HttpResponse.ok(dashboard);
    }

    @Get("/users")
    public HttpResponse<List<Map<String, Object>>> getAllUsers() {
        
        List<User> users = userService.findAllUsers();
        
        List<Map<String, Object>> userList = users.stream()
            .map(user -> {
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("id", user.getId());
                userInfo.put("username", user.getUsername());
                userInfo.put("email", user.getEmail());
                userInfo.put("roles", user.getRoles());
                userInfo.put("createdAt", user.getCreatedAt());
                return userInfo;
            })
            .toList();
            
        return HttpResponse.ok(userList);
    }

    @Post("/users/{userId}/promote")
    public HttpResponse<String> promoteUserToAdmin(@PathVariable Long userId) {
        
        try {
            userService.addRoleToUser(userId, Role.ROLE_ADMIN);
            return HttpResponse.ok("User promoted to admin successfully!");
        } catch (Exception e) {
            return HttpResponse.badRequest().body("Error promoting user: " + e.getMessage());
        }
    }

    @Get("/stats")
    public HttpResponse<Map<String, Object>> getSystemStats() {
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userService.countUsers());
        stats.put("adminUsers", userService.countUsersByRole(Role.ROLE_ADMIN));
        stats.put("regularUsers", userService.countUsersByRole(Role.ROLE_USER));
        stats.put("recentUsers", userService.getRecentUsers(5));
        
        return HttpResponse.ok(stats);
    }
}