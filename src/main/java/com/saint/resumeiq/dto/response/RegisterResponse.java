package com.saint.resumeiq.dto.response;

import com.saint.resumeiq.enums.Role;

public record RegisterResponse(
        String firstName,
        String lastName,
        String username,
        String email,
        Role role
) {}