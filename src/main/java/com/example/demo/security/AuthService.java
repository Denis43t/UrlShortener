package com.example.demo.security;


import org.springframework.stereotype.Service;

//при реалізації інтерфейсу анотація тут непотрібна, тільки над класом
@Service
public interface AuthService {
    String generateToken(String username);
    boolean validateToken(String token);
    String extractUsernameFromToken(String token);
    String refreshToken(String token);
}
