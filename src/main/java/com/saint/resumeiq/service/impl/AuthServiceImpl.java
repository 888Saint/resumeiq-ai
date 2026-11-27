package com.saint.resumeiq.service.impl;

import com.saint.resumeiq.advice.GlobalExceptionHandler;
import com.saint.resumeiq.dto.request.LoginRequest;
import com.saint.resumeiq.dto.request.RegisterRequest;
import com.saint.resumeiq.dto.response.AuthResponse;
import com.saint.resumeiq.dto.response.RegisterResponse;
import com.saint.resumeiq.entity.User;
import com.saint.resumeiq.enums.Role;
import com.saint.resumeiq.exception.ResourceAlreadyExistsException;
import com.saint.resumeiq.repository.UserRepository;
import com.saint.resumeiq.service.AuthService;
import com.saint.resumeiq.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.usernameOrEmail())
                .or(() -> userRepository.findByEmail(request.usernameOrEmail()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, "Bearer");
    }
}