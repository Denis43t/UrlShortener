package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.util.MessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final long EXPIRATION_TIME = 86400000L;


    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(jwtSecret));
    }


    public boolean validateToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            String subject = jwt.getSubject();
            Date expiration = jwt.getExpiresAt();

            Date now = new Date();

            if (expiration != null && now.before(expiration)) {
                return subject != null && !subject.isEmpty();
            } else {
                return false;
            }
        } catch (JWTDecodeException e) {
            log.warn(MessageProvider.INCORRECT_TOKEN_MESSAGE);
            log.error("JWT Decode Exception", e);
            return false;
        }
    }


    public String extractUsernameFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (JWTDecodeException e) {
            log.error(e.getMessage());
            return "";
        }
    }


    public String refreshToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);

            String subject = decodedJWT.getSubject();
            Date expiration = decodedJWT.getExpiresAt();

            if (expiration != null && expiration.before(new Date())) {
                // Token is expired, generate a new one
                Algorithm algorithm = Algorithm.HMAC256("secret");  // Use your actual secret key here
                // Set new expiration (1 hour from now)

                return JWT.create()
                        .withSubject(subject)
                        .withIssuedAt(new Date())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))  // Set new expiration (1 hour from now)
                        .sign(algorithm);
            }
        } catch (JWTDecodeException e) {
            log.error(e.getMessage());
        }

        return "";
    }


    public String extractTokenFromHeader(String authorizationHeader) {
        return (authorizationHeader != null
                && authorizationHeader.startsWith("Bearer "))
                ? authorizationHeader.substring(7)
                : null;
    }
}
