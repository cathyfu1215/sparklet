package com.sparklet.model;

public class PageInfo {
    private String pageName;
    private String description;
    private String status;
    private String comingSoon;

    // Constructors
    public PageInfo() {}

    public PageInfo(String pageName, String description, String status, String comingSoon) {
        this.pageName = pageName;
        this.description = description;
        this.status = status;
        this.comingSoon = comingSoon;
    }

    // Getters and Setters
    public String getPageName() { return pageName; }
    public void setPageName(String pageName) { this.pageName = pageName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getComingSoon() { return comingSoon; }
    public void setComingSoon(String comingSoon) { this.comingSoon = comingSoon; }
}
