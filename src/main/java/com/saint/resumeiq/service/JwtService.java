package com.saint.resumeiq.service;

import com.saint.resumeiq.entity.User;

public interface JwtService {
    String generateToken(User user);
}