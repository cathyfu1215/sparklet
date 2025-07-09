package com.sparklet.controller;

import com.sparklet.dto.ApiResponse;
import com.sparklet.dto.payment.PaymentIntentRequest;
import com.sparklet.dto.payment.PaymentIntentResponse;
import com.sparklet.dto.payment.PaymentConfirmRequest;
import com.sparklet.model.User;
import com.sparklet.security.UserPrincipal;
import com.sparklet.service.PaymentService;
import com.sparklet.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/create-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentIntentRequest request, Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            User user = userDetailsService.findByEmail(userPrincipal.getEmail());
            
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            PaymentIntentResponse response = paymentService.createPaymentIntent(request, user.getId());
            return ResponseEntity.ok(ApiResponse.success("Payment intent created", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("Failed to create payment intent"));
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPayment(@RequestBody PaymentConfirmRequest request, Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            User user = userDetailsService.findByEmail(userPrincipal.getEmail());
            
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            boolean success = paymentService.confirmPayment(request, user.getId());
            if (success) {
                // Return updated user data
                User updatedUser = userDetailsService.findByEmail(userPrincipal.getEmail());
                updatedUser.setPassword(null); // Don't send password
                return ResponseEntity.ok(ApiResponse.success("Payment confirmed and account upgraded", updatedUser));
            } else {
                return ResponseEntity.badRequest().body(ApiResponse.error("Payment confirmation failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("Failed to confirm payment"));
        }
    }

    @GetMapping("/plans")
    public ResponseEntity<?> getPlans() {
        Map<String, Double> plans = paymentService.getPlanPrices();
        return ResponseEntity.ok(ApiResponse.success("Payment plans", plans));
    }
}
