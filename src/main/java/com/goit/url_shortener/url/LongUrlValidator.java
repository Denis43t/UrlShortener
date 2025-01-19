package com.goit.url_shortener.url;

import org.springframework.stereotype.Service;

/**
 * Service class responsible for validating long URLs.
 * This class contains a method to check if a given long URL starts with 'http://' or 'https://'.
 *
 * <p> The {@code isValid} method verifies that the provided URL:
 * <ul>
 *   <li>Is not null or empty.</li>
 *   <li>Starts with 'http://' or 'https://'.</li>
 * </ul>
 *
 * <p> This class uses a regular expression to perform the validation.</p>
 */
@Service
public class LongUrlValidator {

    /**
     * Validates whether the provided long URL starts with 'http://' or 'https://'.
     *
     * @param longUrl - The long URL to validate.
     * @return {@code true} if the URL is valid (starts with 'http://' or 'https://'), {@code false} otherwise.
     */
    public boolean isValid(String longUrl) {
        if (longUrl == null || longUrl.isEmpty()) {
            return false;
        }

        String URL_REGEX = "^(http://|https://).+";
        return longUrl.matches(URL_REGEX);
    }
}
