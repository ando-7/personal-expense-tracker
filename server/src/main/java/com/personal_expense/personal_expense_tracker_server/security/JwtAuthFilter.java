package com.personal_expense.personal_expense_tracker_server.security;

import com.personal_expense.personal_expense_tracker_server.service.TokenBlackListService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final TokenBlackListService tokenBlackListService;

    public JwtAuthFilter(JwtUtil jwtUtil, TokenBlackListService tokenBlackListService) {
        this.jwtUtil = jwtUtil;
        this.tokenBlackListService = tokenBlackListService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtUtil.extractToken(request);

        if(token != null){
            if(tokenBlackListService.isBlacklisted(token)){
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token revoked");
                return;
            }

            try {
                if(jwtUtil.validateToken(token, jwtUtil.extractEmail(token))){
                    String email = jwtUtil.extractEmail(token);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("Authenticated user: " + email);
                }
            } catch (Exception e) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
