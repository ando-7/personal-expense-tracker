package com.personal_expense.personal_expense_tracker_server.service;

import com.personal_expense.personal_expense_tracker_server.dto.LoginRequest;
import com.personal_expense.personal_expense_tracker_server.dto.LoginResponse;
import com.personal_expense.personal_expense_tracker_server.dto.RegisterRequest;
import com.personal_expense.personal_expense_tracker_server.model.User;
import com.personal_expense.personal_expense_tracker_server.repository.UserRepository;
import com.personal_expense.personal_expense_tracker_server.security.JwtUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenBlackListService tokenBlacklistService;

    public AuthService(UserRepository userRepository, TokenBlackListService tokenBlackListService, JwtUtil jwtUtil, TokenBlackListService tokenBlacklistService) {
        this.userRepository = userRepository;
        this.tokenBlacklistService = tokenBlacklistService;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    public User register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getemail())
                .orElseThrow(() -> new EntityNotFoundException("User with email " + loginRequest.getemail() + " not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        long expiresAt = System.currentTimeMillis() + 3600000;

        return new LoginResponse(token, user.getEmail(), user.getId(), expiresAt);
    }

    public void logout(@RequestHeader("Authorization") String authHeader) {
        // Extract token from "Bearer <token>"
        String token = authHeader.substring(7);
        tokenBlacklistService.add(token);
    }

    @Bean
    CommandLineRunner commandLineRunner(AuthService authService) {
        return args -> {
            RegisterRequest request = new RegisterRequest(
                    "a.wolkow@gmail.com",
                    "Wolkow-22",
                    "Aleksandra",
                    "Wolkow"
            );
            authService.register(request);
        };
    }

}
