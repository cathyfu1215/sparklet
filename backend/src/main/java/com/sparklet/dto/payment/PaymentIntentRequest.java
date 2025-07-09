package com.sparklet.dto.payment;

public class PaymentIntentRequest {
    private String planType; // "premium_monthly", "premium_yearly"
    private String paymentMethod; // "card", "paypal"

    // Constructors
    public PaymentIntentRequest() {}

    public PaymentIntentRequest(String planType, String paymentMethod) {
        this.planType = planType;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public String getPlanType() { return planType; }
    public void setPlanType(String planType) { this.planType = planType; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}
