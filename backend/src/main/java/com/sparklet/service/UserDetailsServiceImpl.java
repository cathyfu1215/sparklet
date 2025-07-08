package com.sparklet.service;

import com.sparklet.model.User;
import com.sparklet.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final Map<String, User> users = new HashMap<>();
    private final AtomicLong userIdCounter = new AtomicLong(1);
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDetailsServiceImpl() {
        // Initialize with some mock users
        createMockUser("tom@example.com", "password123", "Tom", "Cook");
        createMockUser("jane@example.com", "password123", "Jane", "Smith");
        createMockUser("john@example.com", "password123", "John", "Doe");
    }

    private void createMockUser(String email, String password, String firstName, String lastName) {
        User user = new User();
        user.setId(userIdCounter.getAndIncrement());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setProfilePhotoUrl("https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        users.put(email, user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = users.get(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return UserPrincipal.create(user);
    }

    public User findByEmail(String email) {
        return users.get(email);
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(userIdCounter.getAndIncrement());
            user.setCreatedAt(LocalDateTime.now());
        }
        user.setUpdatedAt(LocalDateTime.now());
        users.put(user.getEmail(), user);
        return user;
    }

    public boolean existsByEmail(String email) {
        return users.containsKey(email);
    }
}
