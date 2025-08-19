package com.personal_expense.personal_expense_tracker_server.dto;

public class LoginResponse {
    private String token;
    private String email;
    private Long userId;
    private long expiresAt;

    public LoginResponse(String token, String email, Long userId, long expiresAt) {
        this.token = token;
        this.email = email;
        this.userId = userId;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }
}
