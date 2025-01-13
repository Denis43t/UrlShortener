package com.example.demo.security;

import org.springframework.stereotype.Service;


//заглушки; методи потрібно реалізувати;
@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public String generateToken(String username) {
        return "token";
    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }

    @Override
    public String extractUsernameFromToken(String token) {
        return "";
    }

    @Override
    public String refreshToken(String token) {
        return "";
    }
}
