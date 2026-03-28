package com.learns.blogapi.controller;

import com.learns.blogapi.dto.AuthResponse;
import com.learns.blogapi.dto.LoginRequest;
import com.learns.blogapi.dto.RegisterRequest;
import com.learns.blogapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // POST /auth/register
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @jakarta.validation.Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @jakarta.validation.Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}