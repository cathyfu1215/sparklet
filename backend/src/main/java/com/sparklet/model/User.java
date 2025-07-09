package com.sparklet.model;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String profilePhotoUrl;
    private String mbtiType;
    private String linkedinUrl;
    private String personalWebsiteUrl;
    private String githubUrl;
    private AccountType accountType;
    private LocalDateTime premiumExpiryDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public User() {}

    public User(Long id, String email, String firstName, String lastName, String profilePhotoUrl) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhotoUrl = profilePhotoUrl;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getProfilePhotoUrl() { return profilePhotoUrl; }
    public void setProfilePhotoUrl(String profilePhotoUrl) { this.profilePhotoUrl = profilePhotoUrl; }

    public String getMbtiType() { return mbtiType; }
    public void setMbtiType(String mbtiType) { this.mbtiType = mbtiType; }

    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }

    public String getPersonalWebsiteUrl() { return personalWebsiteUrl; }
    public void setPersonalWebsiteUrl(String personalWebsiteUrl) { this.personalWebsiteUrl = personalWebsiteUrl; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public LocalDateTime getPremiumExpiryDate() { return premiumExpiryDate; }
    public void setPremiumExpiryDate(LocalDateTime premiumExpiryDate) { this.premiumExpiryDate = premiumExpiryDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Helper method to check if user has active premium
    public boolean hasActivePremium() {
        return accountType == AccountType.PREMIUM && 
               premiumExpiryDate != null && 
               premiumExpiryDate.isAfter(LocalDateTime.now());
    }
}
