package com.goit.url_shortener.url;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the `ShortUrlGenerator` class.
 */
@SpringBootTest
public class ShortUrlGeneratorTest {
    /**
     * Array containing all possible characters for the short URL.
     */
    private static final char[] CHARACTERS = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    /**
     * Minimum length of the generated short URL.
     */
    private static final int MIN_SHORT_URL_LENGTH = 6;

    /**
     * Maximum length of the generated short URL.
     */
    private static final int MAX_SHORT_URL_LENGTH = 8;


    @Autowired
    private ShortUrlGenerator shortUrlGenerator;

    /**
     * Test to ensure that the generated short URL starts with "https://".
     */
    @Test
    public void testGenerateShortUrl_StartsWithHttps() {
        String shortUrl = shortUrlGenerator.generateShortUrl();
        assertTrue(shortUrl.startsWith("https://"), "Generated URL should start with 'https://'");
    }

    /**
     * Test to verify that the length of the generated short URL is within the specified range.
     */
    @Test
    public void testGenerateShortUrl_Length() {
        String shortUrl = shortUrlGenerator.generateShortUrl();
        String actualShortUrl = shortUrl.replace("https://", "");

        assertTrue(actualShortUrl.length() >= MIN_SHORT_URL_LENGTH &&
                        actualShortUrl.length() <= MAX_SHORT_URL_LENGTH,
                "Generated URL should be between " + MIN_SHORT_URL_LENGTH +
                        " and " + MAX_SHORT_URL_LENGTH + " characters long.");
    }

    /**
     * Test to ensure that the generated short URL consists of valid characters.
     */
    @Test
    public void testGenerateShortUrl_ValidCharacters() {
        String shortUrl = shortUrlGenerator.generateShortUrl();
        for (char c : shortUrl.substring(8).toCharArray()) {
            assertTrue(isValidCharacter(c), "Character in the short URL should be valid.");
        }
    }

    /**
     * Helper method to check if a character is valid for the short URL.
     *
     * @param c Character to check.
     * @return `true` if the character is valid, `false` otherwise.
     */
    private boolean isValidCharacter(char c) {
        for (char validChar : CHARACTERS) {
            if (c == validChar) {
                return true;
            }
        }
        return false;
    }
}
