package com.example.demo.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the UserValidator class.
 *
 * This class contains test cases to verify the behavior of the UserValidator,
 * which validates usernames and passwords based on specific criteria.
 */
public class UserValidatorTest {

    private final UserValidator userValidator = new UserValidator();

    /**
     * Tests the username format validation for a valid username.
     *
     * <p> This test verifies that a username that meets the required format
     * (between 4 and 50 characters) is recognized as valid.
     */
    @Test
    public void testIsValidUsernameFormat_Valid() {
        String validUsername = "validUsername";
        assertTrue(userValidator.isValidUsernameFormat(validUsername));
    }

    /**
     * Tests the username format validation for an invalid username.
     *
     * <p> This test verifies that usernames that are too short (less than 4 characters)
     * or too long (more than 50 characters) are recognized as invalid.
     */
    @Test
    public void testIsValidUsernameFormat_Invalid() {
        String shortUsername = "usr";
        assertFalse(userValidator.isValidUsernameFormat(shortUsername));

        String longUsername = "a".repeat(51);
        assertFalse(userValidator.isValidUsernameFormat(longUsername));
    }

    /**
     * Tests the password format validation for a valid password.
     *
     * <p> This test verifies that a password that contains at least one uppercase letter,
     * one lowercase letter, and one digit is recognized as valid.
     */
    @Test
    public void testIsValidPasswordFormat_Valid() {
        String validPassword = "Valid123";
        assertTrue(userValidator.isValidPasswordFormat(validPassword));
    }

    /**
     * Tests the password format validation for an invalid password.
     *
     * <p> This test verifies that passwords that are too short (less than 6 characters)
     * or lack the necessary complexity (missing uppercase, lowercase, or digit)
     * are recognized as invalid.
     */
    @Test
    public void testIsValidPasswordFormat_Invalid() {
        String shortPassword = "Short1";
        assertFalse(userValidator.isValidPasswordFormat(shortPassword));

        String noUppercasePassword = "valid123";
        assertFalse(userValidator.isValidPasswordFormat(noUppercasePassword));

        String noLowercasePassword = "VALID123";
        assertFalse(userValidator.isValidPasswordFormat(noLowercasePassword));

        String noDigitPassword = "ValidPassword";
        assertFalse(userValidator.isValidPasswordFormat(noDigitPassword));
    }
}

