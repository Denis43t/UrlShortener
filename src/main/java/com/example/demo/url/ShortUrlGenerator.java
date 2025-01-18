package com.example.demo.url;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Service class for generating short URLs.
 */
@Service
public class ShortUrlGenerator {

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

    /**
     * Generates a random short URL.
     * <p>
     * The generated short URL will have a length between {@link #MIN_SHORT_URL_LENGTH}
     * and {@link #MAX_SHORT_URL_LENGTH} characters and consists of randomly selected characters.
     *
     * @return A string representing the generated short URL with the format "https://<shortUrl>"
     */
    public String generateShortUrl() {
        Random random = new Random();
        int shortUrlLength =
                random.nextInt(MAX_SHORT_URL_LENGTH - MIN_SHORT_URL_LENGTH + 1)
                        + MIN_SHORT_URL_LENGTH;
        StringBuilder shortUrl = new StringBuilder();

        for (int i = 0; i < shortUrlLength; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length);
            shortUrl.append(CHARACTERS[randomIndex]);
        }
        return "https://" + shortUrl;
    }
}

