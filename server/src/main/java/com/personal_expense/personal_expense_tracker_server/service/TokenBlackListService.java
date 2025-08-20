package com.personal_expense.personal_expense_tracker_server.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlackListService {
    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();
    private static final long DEFAULT_TOKEN_EXPIRY_MS = 60 * 60 * 1000;

    public void add(String token) {
        blacklist.add(token);
        // Auto-remove after expiration
        new Thread(() -> {
            try {
                Thread.sleep(DEFAULT_TOKEN_EXPIRY_MS);
                blacklist.remove(token);
            } catch (InterruptedException ignored) {}
        }).start();
    }

    // Check if token is blacklisted
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
