package com.sparklet.controller;

import com.sparklet.entity.User;
import com.sparklet.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.List; 
import java.util.Map;
import java.util.Optional;

@Controller("/api/profile")
@Secured("isAuthenticated()")
public class UserProfileController {

    @Inject
    private UserService userService;

    @Get("/me")
    public HttpResponse<Map<String, Object>> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOptional = userService.findByUsername(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            Map<String, Object> profile = new HashMap<>();
            profile.put("id", user.getId());
            profile.put("username", user.getUsername());
            profile.put("email", user.getEmail());
            profile.put("roles", user.getRoles());
            profile.put("createdAt", user.getCreatedAt());
            
            return HttpResponse.ok(profile);
        }
        
        return HttpResponse.notFound();
    }


    
@Get("/debug-token")
public HttpResponse<Map<String, Object>> debugToken(Authentication authentication) {
    Map<String, Object> debug = new HashMap<>();
    debug.put("username", authentication.getName());
    debug.put("roles", authentication.getRoles());
    debug.put("attributes", authentication.getAttributes());
    debug.put("allClaims", authentication.getAttributes());
    
    return HttpResponse.ok(debug);
}

@Get("/debug-users")
@Secured("isAuthenticated()")
public HttpResponse<Map<String, Object>> debugUsers(Authentication authentication) {
    
    Map<String, Object> debug = new HashMap<>();
    debug.put("currentUser", authentication.getName());
    debug.put("userRoles", authentication.getRoles());
    debug.put("hasAdminRole", authentication.getRoles().contains("ROLE_ADMIN"));
    debug.put("allAttributes", authentication.getAttributes());
    
    // Only allow admins to see all users for debugging
    if (!authentication.getRoles().contains("ROLE_ADMIN")) {
        debug.put("error", "Admin access required");
        debug.put("actualRoles", authentication.getRoles());
        debug.put("hint", "User needs ROLE_ADMIN to access this endpoint");
        return HttpResponse.unauthorized().body(debug); // Return Map instead of String
    }
    
    List<User> allUsers = userService.findAllUsers();
    
    debug.put("totalUsers", allUsers.size());
    debug.put("usernames", allUsers.stream().map(User::getUsername).toList());
    debug.put("emails", allUsers.stream().map(User::getEmail).toList());
    
    return HttpResponse.ok(debug);
}
    
}