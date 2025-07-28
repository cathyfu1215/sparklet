package com.sparklet.controller;

import com.sparklet.dto.JwtResponse;
import com.sparklet.dto.LoginRequest;
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

@Controller("/api/auth")
public class AuthController {

    @Inject
    private TokenGenerator tokenGenerator;

    @Post("/login")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<?> authenticateUser(@Body LoginRequest loginRequest) {
        
        // For now, let's create a simple hardcoded authentication
        // We'll replace this with real user authentication later
        if ("admin".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())) {
            
            // Generate JWT token
            Map<String, Object> claims = new HashMap<>();
            claims.put("sub", loginRequest.getUsername());
            claims.put("roles", "ROLE_ADMIN");
            
            String token = tokenGenerator.generateToken(claims).orElse("");
            
            JwtResponse jwtResponse = new JwtResponse(
                token,
                1L,
                loginRequest.getUsername(),
                "admin@sparklet.com"
            );
            
            return HttpResponse.ok(jwtResponse);
        }
        
        return HttpResponse.unauthorized();
    }

    @Post("/signup")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<String> registerUser() {
        // Placeholder for user registration
        return HttpResponse.ok("User registered successfully! (Coming soon)");
    }
}