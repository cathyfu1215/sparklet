package com.sparklet.dto.payment;

public class PaymentConfirmRequest {
    private String paymentIntentId;
    private String planType;

    // Constructors
    public PaymentConfirmRequest() {}

    public PaymentConfirmRequest(String paymentIntentId, String planType) {
        this.paymentIntentId = paymentIntentId;
        this.planType = planType;
    }

    // Getters and Setters
    public String getPaymentIntentId() { return paymentIntentId; }
    public void setPaymentIntentId(String paymentIntentId) { this.paymentIntentId = paymentIntentId; }

    public String getPlanType() { return planType; }
    public void setPlanType(String planType) { this.planType = planType; }
}
