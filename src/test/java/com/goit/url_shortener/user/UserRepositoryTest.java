package com.goit.url_shortener.user;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UserRepository class.
 * <p>
 * This class contains test cases to verify the behavior of the UserRepository methods.
 * It tests basic CRUD operations, such as saving a user and retrieving them by username.
 */
@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Sets up the necessary environment for the tests by initializing and starting a PostgreSQL container.
     *
     * This method creates a new instance of the PostgreSQL container using the official Docker image
     * (`postgres:latest`). It configures the container to use a test database named "test_shortener_db",
     * sets the username to "test_user", and the password to "test_password".
     *
     * The PostgreSQL container is then started, providing a clean and isolated database environment
     * for running the unit tests.
     */
    @BeforeEach
    public void setUp() {
        PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("test_shortener_db")
                .withUsername("test_user")
                .withPassword("test_password");
        postgreSQLContainer.start();
    }

    /**
     * Tests the findByUsername method when a user exists in the database.
     *
     * <p> The test saves a user to the database, then attempts to retrieve that user by username.
     * It verifies that the correct user is found and that the returned username matches the expected value.
     */
    @Test
    void testFindByUsername() {
        userRepository.save(User.builder()
                .username("testuser")
                .password("test885Pass")
                .build());

        Optional<User> foundUser = userRepository.findByUsername("testuser");

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
        Optional<User> foundUser = userRepository.findByUsername("nonexistentuser");
        assertFalse(foundUser.isPresent());
    }
}
