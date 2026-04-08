package com.wellnest.controller;

import com.wellnest.dto.AuthDTOs;
import com.wellnest.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDTOs.AuthResponse> register(@Valid @RequestBody AuthDTOs.RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTOs.AuthResponse> login(@Valid @RequestBody AuthDTOs.LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/admin-login")
    public ResponseEntity<AuthDTOs.AuthResponse> adminLogin(@Valid @RequestBody AuthDTOs.AdminLoginRequest req) {
        return ResponseEntity.ok(authService.adminLogin(req));
    }
}
