package com.personal_expense.personal_expense_tracker_server.controller;

import com.personal_expense.personal_expense_tracker_server.dto.LoginRequest;
import com.personal_expense.personal_expense_tracker_server.dto.LoginResponse;
import com.personal_expense.personal_expense_tracker_server.dto.RegisterRequest;
import com.personal_expense.personal_expense_tracker_server.model.User;
import com.personal_expense.personal_expense_tracker_server.security.JwtUtil;
import com.personal_expense.personal_expense_tracker_server.service.AuthService;
import com.personal_expense.personal_expense_tracker_server.service.TokenBlackListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) {
        authService.logout(request, response);
    }
}
