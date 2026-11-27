package com.saint.resumeiq.dto.response;

public record AuthResponse(
        String token,
        String tokenType
) {}