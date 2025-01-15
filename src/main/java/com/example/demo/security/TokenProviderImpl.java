package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;


//попередня реалізація
@Service
public class TokenProviderImpl implements TokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final long EXPIRATION_TIME = 86400000L;

    @Override
    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    @Override
    public boolean validateToken(String token) {
        return JWT.decode(token).getClaim("sub").asBoolean();
    }

    @Override
    public String extractUsernameFromToken(String token) {
        return JWT.decode(token).getSubject();
    }

    @Override
    public String refreshToken(String token) {
        return JWT.decode(token).getClaim("sub").asString();
    }

    @Override
    public String extractTokenFromHeader(String authorizationHeader) {
        return (authorizationHeader != null
                && authorizationHeader.startsWith("Bearer "))
                ? authorizationHeader.substring(7)
                : null;
    }
}
