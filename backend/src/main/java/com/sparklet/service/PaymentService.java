package com.sparklet.service;

import com.sparklet.model.Payment;
import com.sparklet.model.User;
import com.sparklet.model.AccountType;
import com.sparklet.dto.payment.PaymentIntentRequest;
import com.sparklet.dto.payment.PaymentIntentResponse;
import com.sparklet.dto.payment.PaymentConfirmRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PaymentService {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private final Map<String, Payment> payments = new HashMap<>();
    private final AtomicLong paymentIdCounter = new AtomicLong(1);

    // Pricing configuration
    private static final Map<String, Double> PLAN_PRICES = Map.of(
        "premium_monthly", 9.99,
        "premium_yearly", 99.99
    );

    public PaymentIntentResponse createPaymentIntent(PaymentIntentRequest request, Long userId) {
        // Simulate payment intent creation (in real app, this would call Stripe)
        String paymentIntentId = "pi_" + UUID.randomUUID().toString().replace("-", "").substring(0, 24);
        String clientSecret = paymentIntentId + "_secret_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        
        Double amount = PLAN_PRICES.get(request.getPlanType());
        if (amount == null) {
            throw new IllegalArgumentException("Invalid plan type: " + request.getPlanType());
        }

        // Create payment record
        Payment payment = new Payment(userId, paymentIntentId, amount, "usd", "requires_payment_method");
        payment.setId(paymentIdCounter.getAndIncrement());
        payment.setPaymentMethod(request.getPaymentMethod());
        payments.put(paymentIntentId, payment);

        return new PaymentIntentResponse(clientSecret, paymentIntentId, amount, "usd", "requires_payment_method");
    }

    public boolean confirmPayment(PaymentConfirmRequest request, Long userId) {
        Payment payment = payments.get(request.getPaymentIntentId());
        if (payment == null || !payment.getUserId().equals(userId)) {
            return false;
        }

        // Simulate payment confirmation (in real app, this would verify with Stripe)
        payment.setStatus("succeeded");
        payment.setUpdatedAt(LocalDateTime.now());

        // Upgrade user account
        User user = userDetailsService.findById(userId);
        if (user != null) {
            user.setAccountType(AccountType.PREMIUM);
            
            // Set expiry based on plan type
            LocalDateTime expiryDate;
            if ("premium_monthly".equals(request.getPlanType())) {
                expiryDate = LocalDateTime.now().plusMonths(1);
            } else if ("premium_yearly".equals(request.getPlanType())) {
                expiryDate = LocalDateTime.now().plusYears(1);
            } else {
                expiryDate = LocalDateTime.now().plusMonths(1); // Default to monthly
            }
            
            user.setPremiumExpiryDate(expiryDate);
            userDetailsService.save(user);
        }

        return true;
    }

    public Payment getPayment(String paymentIntentId) {
        return payments.get(paymentIntentId);
    }

    public Map<String, Double> getPlanPrices() {
        return PLAN_PRICES;
    }
}
