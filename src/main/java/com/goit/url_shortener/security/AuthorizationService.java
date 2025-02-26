package com.goit.url_shortener.security;

import com.goit.url_shortener.user.User;
import com.goit.url_shortener.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for handling user authorization and token validation.
 */
@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UserRepository userRepository;

    private final JwtTokenProvider tokenProvider;

    /**
     * Extracts the authorized user from the provided authorization header.
     *
     * @param authorizationHeader The authorization header containing the JWT token.
     * @return An Optional containing the authenticated user if the token is valid, or an empty Optional otherwise.
     */
    public Optional<User> getAuthorizedUser(String authorizationHeader) {
        String token = tokenProvider.extractTokenFromHeader(authorizationHeader);

        if (token != null && tokenProvider.validateToken(token)) {
            String username = tokenProvider.extractUsernameFromToken(token);
            return userRepository.findByUsername(username);
        }
        return Optional.empty();
    }
}
