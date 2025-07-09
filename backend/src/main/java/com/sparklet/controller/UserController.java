package com.sparklet.controller;

import com.sparklet.dto.ApiResponse;
import com.sparklet.model.AccountType;
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
        
        // Ensure accountType is set for existing users (migration fix)
        if (user.getAccountType() == null) {
            user.setAccountType(AccountType.BASIC);
            userDetailsService.save(user);
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
        
        // Validate URLs if provided
        if (updatedUser.getLinkedinUrl() != null && !updatedUser.getLinkedinUrl().isEmpty() && !isValidUrl(updatedUser.getLinkedinUrl())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid LinkedIn URL format"));
        }
        if (updatedUser.getPersonalWebsiteUrl() != null && !updatedUser.getPersonalWebsiteUrl().isEmpty() && !isValidUrl(updatedUser.getPersonalWebsiteUrl())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid personal website URL format"));
        }
        if (updatedUser.getGithubUrl() != null && !updatedUser.getGithubUrl().isEmpty() && !isValidUrl(updatedUser.getGithubUrl())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid GitHub URL format"));
        }
        
        // Update only allowed fields (account type updates should be handled separately for security)
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setProfilePhotoUrl(updatedUser.getProfilePhotoUrl());
        existingUser.setMbtiType(updatedUser.getMbtiType());
        existingUser.setLinkedinUrl(updatedUser.getLinkedinUrl());
        existingUser.setPersonalWebsiteUrl(updatedUser.getPersonalWebsiteUrl());
        existingUser.setGithubUrl(updatedUser.getGithubUrl());
        // Note: accountType and premiumExpiryDate are not updated here for security reasons
        
        User saved = userDetailsService.save(existingUser);
        saved.setPassword(null); // Don't send password in response
        
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", saved));
    }
    
    private boolean isValidUrl(String url) {
        try {
            new java.net.URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
