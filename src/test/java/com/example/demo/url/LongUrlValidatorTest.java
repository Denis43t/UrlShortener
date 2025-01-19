package com.example.demo.url;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LongUrlValidatorTest {

    private final LongUrlValidator validator = new LongUrlValidator();

    /**
     * Test case for validating a long URL that starts with 'http://'.
     */
    @Test
    public void testValidHttpUrl() {
        String httpUrl = "http://example.com";
        assertTrue(validator.isValid(httpUrl), "URL starting with 'http://' should be valid.");
    }

    /**
     * Test case for validating a long URL that starts with 'https://'.
     */
    @Test
    public void testValidHttpsUrl() {
        String httpsUrl = "https://secure.example.com";
        assertTrue(validator.isValid(httpsUrl), "URL starting with 'https://' should be valid.");
    }

    /**
     * Test case for validating a long URL that does not start with 'http://' or 'https://'.
     */
    @Test
    public void testInvalidUrl() {
        String invalidUrl = "ftp://example.com";
        assertFalse(validator.isValid(invalidUrl), "URL starting with 'ftp://' should be invalid.");
    }

    /**
     * Test case for validating a null URL.
     */
    @Test
    public void testNullUrl() {
        assertFalse(validator.isValid(null), "A null URL should be invalid.");
    }

    /**
     * Test case for validating an empty URL.
     */
    @Test
    public void testEmptyUrl() {
        assertFalse(validator.isValid(""), "An empty URL should be invalid.");
    }

    /**
     * Test case for validating a long URL that starts with spaces (invalid).
     */
    @Test
    public void testUrlWithSpaces() {
        String urlWithSpaces = " https://example.com";
        assertFalse(validator.isValid(urlWithSpaces), "URL starting with spaces should be invalid.");
    }
}

