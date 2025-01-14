package com.example.demo.security;


public interface TokenProvider {
    String generateToken(String username);
    boolean validateToken(String token);
    String extractUsernameFromToken(String token);
    String refreshToken(String token);
}
