package com.personal_expense.personal_expense_tracker_server.service;

import com.personal_expense.personal_expense_tracker_server.dto.LoginRequest;
import com.personal_expense.personal_expense_tracker_server.dto.LoginResponse;
import com.personal_expense.personal_expense_tracker_server.dto.RegisterRequest;
import com.personal_expense.personal_expense_tracker_server.model.User;
import com.personal_expense.personal_expense_tracker_server.repository.UserRepository;
import com.personal_expense.personal_expense_tracker_server.security.JwtUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenBlackListService tokenBlackListService;

    public AuthService(UserRepository userRepository, TokenBlackListService tokenBlackListService, JwtUtil jwtUtil, TokenBlackListService tokenBlacklistService) {
        this.userRepository = userRepository;
        this.tokenBlackListService = tokenBlackListService;
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

    public Map<String, String> logout(HttpServletRequest request,
                                      HttpServletResponse response) {
        String token = jwtUtil.extractToken(request);

        if (token == null || token.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            return Map.of("error", "No token found");
        }

        if (tokenBlackListService.isBlacklisted(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            return Map.of("error", "Token already revoked");
        }

        try {
            String email = jwtUtil.extractEmail(token);
            if (!jwtUtil.validateToken(token, email)){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return Map.of("error", "Invalid or expired token");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 code
            return Map.of("error", "Invalid token format");
        }

        tokenBlackListService.add(token);
        response.setStatus(HttpServletResponse.SC_OK);
        return Map.of("message", "Logged out successfully");
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
