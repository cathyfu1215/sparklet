package com.sparklet.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller("/api/test")
public class TestController {

    @Get("/public")
    @Secured(SecurityRule.IS_ANONYMOUS)  // Public endpoint - no authentication required
    public String publicAccess() {
        return "Public Content - Micronaut Backend is Working!";
    }

    @Get("/user")
    @Secured(SecurityRule.IS_AUTHENTICATED)  // Requires authentication
    public String userAccess() {
        return "User Content - You are authenticated!";
    }

    @Get("/admin")
    @Secured("ROLE_ADMIN")  // Requires ADMIN role
    public String adminAccess() {
        return "Admin Board - Micronaut Backend";
    }
}