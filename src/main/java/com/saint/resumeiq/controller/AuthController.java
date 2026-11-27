package com.saint.resumeiq.controller;

import com.saint.resumeiq.dto.request.LoginRequest;
import com.saint.resumeiq.dto.request.RegisterRequest;
import com.saint.resumeiq.dto.response.AuthResponse;
import com.saint.resumeiq.dto.response.RegisterResponse;
import com.saint.resumeiq.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}