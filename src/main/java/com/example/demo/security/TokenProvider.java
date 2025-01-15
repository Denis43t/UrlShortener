package com.example.demo.security;


import java.net.http.HttpRequest;

public interface TokenProvider {
    String generateToken(String username);
    boolean validateToken(String token);
    String extractUsernameFromToken(String token);
    String refreshToken(String token);
    String extractTokenFromHeader(String authorizationHeader);
}
