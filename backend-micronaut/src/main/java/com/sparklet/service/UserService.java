package com.sparklet.service;

import com.sparklet.entity.Role;
import com.sparklet.entity.User;
import com.sparklet.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

@Singleton
public class UserService {

    @Inject
    private UserRepository userRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    // Existing methods
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User createUser(String username, String email, String rawPassword) {
        return createUser(username, email, rawPassword, Set.of(Role.ROLE_USER));
    }

    public User createUser(String username, String email, String rawPassword, Set<Role> roles) {
        String encodedPassword = encodePassword(rawPassword);
        User user = new User(username, email, encodedPassword, roles);
        return userRepository.save(user);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return checkPassword(rawPassword, encodedPassword);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    // New admin functions for Phase 4
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public long countUsers() {
        return userRepository.count();
    }

    public long countUsersByRole(Role role) {
        return userRepository.findAll().stream()
            .filter(user -> user.getRoles().contains(role))
            .count();
    }

    public List<User> getRecentUsers(int limit) {
        return userRepository.findAll().stream()
            .sorted((u1, u2) -> u2.getCreatedAt().compareTo(u1.getCreatedAt()))
            .limit(limit)
            .toList();
    }

    public User addRoleToUser(Long userId, Role role) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Set<Role> roles = new HashSet<>(user.getRoles());
            roles.add(role);
            user.setRoles(roles);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + userId);
    }

    // Password encoding methods (replacing BCrypt)
    private String encodePassword(String rawPassword) {
        try {
            byte[] salt = new byte[16];
            secureRandom.nextBytes(salt);
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(rawPassword.getBytes());
            
            byte[] saltedHash = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, saltedHash, 0, salt.length);
            System.arraycopy(hashedPassword, 0, saltedHash, salt.length, hashedPassword.length);
            
            return Base64.getEncoder().encodeToString(saltedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encoding password", e);
        }
    }

    private boolean checkPassword(String rawPassword, String encodedPassword) {
        try {
            byte[] saltedHash = Base64.getDecoder().decode(encodedPassword);
            
            byte[] salt = new byte[16];
            System.arraycopy(saltedHash, 0, salt, 0, 16);
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedInput = md.digest(rawPassword.getBytes());
            
            byte[] storedHash = new byte[saltedHash.length - 16];
            System.arraycopy(saltedHash, 16, storedHash, 0, storedHash.length);
            
            return MessageDigest.isEqual(hashedInput, storedHash);
        } catch (Exception e) {
            return false;
        }
    }

}