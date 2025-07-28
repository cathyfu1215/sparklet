package com.sparklet.controller;

import com.sparklet.dto.JwtResponse;
import com.sparklet.dto.LoginRequest;
import com.sparklet.dto.SignupRequest;
import com.sparklet.entity.User;
import com.sparklet.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.token.generator.TokenGenerator;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller("/api/auth")
public class AuthController {

    @Inject
    private TokenGenerator tokenGenerator;

    @Inject
    private UserService userService;

    @Post("/login")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<?> authenticateUser(@Body LoginRequest loginRequest) {
        
        // Find user by username
        Optional<User> userOptional = userService.findByUsername(loginRequest.getUsername());
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Validate password
            if (userService.validatePassword(loginRequest.getPassword(), user.getPassword())) {
                
                // Generate JWT token with user roles
                Map<String, Object> claims = new HashMap<>();
                claims.put("sub", user.getUsername());
                claims.put("userId", user.getId());
                claims.put("email", user.getEmail());
                
                // Convert roles to strings for JWT
                String roles = user.getRoles().stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
                claims.put("roles", roles);
                
                String token = tokenGenerator.generateToken(claims).orElse("");
                
                JwtResponse jwtResponse = new JwtResponse(
                    token,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail()
                );
                
                return HttpResponse.ok(jwtResponse);
            }
        }
        
        return HttpResponse.unauthorized().body("Invalid username or password");
    }

    @Post("/signup")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<?> registerUser(@Body SignupRequest signupRequest) {
        
        // Check if username already exists
        if (userService.existsByUsername(signupRequest.getUsername())) {
            return HttpResponse.badRequest().body("Error: Username is already taken!");
        }

        // Check if email already exists
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return HttpResponse.badRequest().body("Error: Email is already in use!");
        }

        // Create new user
        try {
            User user = userService.createUser(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                signupRequest.getPassword()
            );
            
            return HttpResponse.ok().body("User registered successfully!");
            
        } catch (Exception e) {
            return HttpResponse.serverError().body("Error: Could not create user!");
        }
    }
}