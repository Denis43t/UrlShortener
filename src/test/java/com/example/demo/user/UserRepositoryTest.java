package com.example.demo.user;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UserRepository class.
 *
 * This class contains test cases to verify the behavior of the UserRepository methods.
 * It tests basic CRUD operations, such as saving a user and retrieving them by username.
 */
@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Clears the database before each test to ensure isolation between tests.
     */
    @BeforeEach
    void clearDatabase() {
        userRepository.deleteAll();
    }

    /**
     * Tests the findByUsername method when a user exists in the database.
     *
     * <p> The test saves a user to the database, then attempts to retrieve that user by username.
     * It verifies that the correct user is found and that the returned username matches the expected value.
     */
    @Test
    void testFindByUsername() {
        // Given: A user saved in the database
        userRepository.save(User.builder()
                .username("testuser")
                .password("test885Pass")
                .build());

        // When: Finding the user by username
        Optional<User> foundUser = userRepository.findByUsername("testuser");

        // Then: The user should be found
        assertTrue(foundUser.isPresent());
        assertNotNull(foundUser.get());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    /**
     * Tests the findByUsername method when no user exists in the database.
     *
     * <p> This test attempts to find a user by a username that doesn't exist in the database.
     * It verifies that no user is returned.
     */
    @Test
    void testNotFoundByUsername() {
        // Given: No user in the database

        // When: Trying to find a non-existing user by username
        Optional<User> foundUser = userRepository.findByUsername("nonexistentuser");

        // Then: No user should be found
        assertFalse(foundUser.isPresent());
    }
}
