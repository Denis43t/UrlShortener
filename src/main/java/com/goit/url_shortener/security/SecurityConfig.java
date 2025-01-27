package com.goit.url_shortener.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for the security settings, specifically for password encoding.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Bean definition for the password encoder.
     *
     * This encoder uses BCrypt hashing to securely encode passwords.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Returns a BCryptPasswordEncoder instance for password encoding.
    }
}
