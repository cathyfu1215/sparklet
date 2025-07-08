package com.sparklet.controller;

import com.sparklet.dto.ApiResponse;
import com.sparklet.model.User;
import com.sparklet.security.UserPrincipal;
import com.sparklet.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userDetailsService.findByEmail(userPrincipal.getEmail());
        
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Don't send password in response
        user.setPassword(null);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody User updatedUser, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User existingUser = userDetailsService.findByEmail(userPrincipal.getEmail());
        
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Update only allowed fields
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setProfilePhotoUrl(updatedUser.getProfilePhotoUrl());
        
        User saved = userDetailsService.save(existingUser);
        saved.setPassword(null); // Don't send password in response
        
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", saved));
    }
}
