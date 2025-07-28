package com.sparklet.service;

import com.sparklet.entity.Role;
import com.sparklet.entity.User;
import com.sparklet.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

@Singleton
public class UserService {

    @Inject
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, email, encodedPassword, roles);
        return userRepository.save(user);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}