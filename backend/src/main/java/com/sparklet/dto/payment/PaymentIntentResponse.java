package com.sparklet.dto.payment;

public class PaymentIntentResponse {
    private String clientSecret;
    private String paymentIntentId;
    private Double amount;
    private String currency;
    private String status;

    // Constructors
    public PaymentIntentResponse() {}

    public PaymentIntentResponse(String clientSecret, String paymentIntentId, Double amount, String currency, String status) {
        this.clientSecret = clientSecret;
        this.paymentIntentId = paymentIntentId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
    }

    // Getters and Setters
    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }

    public String getPaymentIntentId() { return paymentIntentId; }
    public void setPaymentIntentId(String paymentIntentId) { this.paymentIntentId = paymentIntentId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
