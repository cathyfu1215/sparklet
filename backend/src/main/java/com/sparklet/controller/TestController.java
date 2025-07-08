package com.sparklet.controller;

import com.sparklet.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public ResponseEntity<?> allAccess() {
        return ResponseEntity.ok(ApiResponse.success("Public Content."));
    }

    @GetMapping("/user")
    public ResponseEntity<?> userAccess() {
        return ResponseEntity.ok(ApiResponse.success("User Content."));
    }
}
