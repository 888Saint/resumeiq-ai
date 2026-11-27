package com.saint.resumeiq.service;

import com.saint.resumeiq.dto.request.LoginRequest;
import com.saint.resumeiq.dto.request.RegisterRequest;
import com.saint.resumeiq.dto.response.AuthResponse;
import com.saint.resumeiq.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}