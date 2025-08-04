package com.sparklet.controller;

import com.sparklet.entity.Role;
import com.sparklet.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

import java.util.Set;

@Controller("/api/setup")
public class SetupController {

    @Inject
    private UserService userService;

    @Post("/create-admin")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<String> createAdminUser() {
        
        // Check if admin already exists
        if (userService.existsByUsername("admin")) {
            return HttpResponse.badRequest().body("Admin user already exists!");
        }
        
        // Create admin user
        userService.createUser(
            "admin", 
            "admin@sparklet.com", 
            "admin123", 
            Set.of(Role.ROLE_USER, Role.ROLE_ADMIN)
        );
        
        return HttpResponse.ok("Admin user created successfully! Username: admin, Password: admin123");
    }
}