package com.goit.url_shortener.url;

import com.goit.url_shortener.user.User;
import com.goit.url_shortener.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for the `UrlRepository` class.
 * <p>
 * This class contains test cases to verify the behavior of the `UrlRepository`,
 * including methods for retrieving URLs from the database.
 */
@SpringBootTest
@Transactional
public class UrlRepositoryTest {
    private User testUser;

    @Autowired
    private UrlRepository urlRepository;

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
    void setUp() {
        PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("test_shortener_db")
                .withUsername("test_user")
                .withPassword("test_password");
        postgreSQLContainer.start();

        testUser = new User(null, "testUser", "testPassword", null);
        userRepository.save(testUser);
    }

    /**
     * Test for the `findUrlByShortUrl` method in `UrlRepository`.
     * This test verifies the behavior when a valid shortened URL is provided and found in the database.
     */
    @Test
    public void testFindUrlByShortUrl_Success() {
        String shortUrl = "shortUrl123";
        Url url = new Url();
        url.setUser(testUser);
        url.setShortUrl(shortUrl);
        url.setLongUrl("http://example.com");

        urlRepository.save(url);

        Url searchUrl = new Url();
        searchUrl.setShortUrl(shortUrl);
        searchUrl.setUser(testUser);

        Optional<Url> result = urlRepository.findUrlByShortUrl(searchUrl.getShortUrl());

        assertTrue(result.isPresent());
        assertEquals("http://example.com", result.get().getLongUrl());
    }

    /**
     * Test for the `findUrlByShortUrl` method in `UrlRepository`.
     * This test verifies the behavior when the shortened URL does not exist in the database.
     */
    @Test
    public void testFindUrlByShortUrl_NotFound() {
        String shortUrl = "nonExistentUrl";

        Optional<Url> result = urlRepository.findUrlByShortUrl(shortUrl);

        assertTrue(result.isEmpty());
    }

    /**
     * Test for the `findAllUrlsByUsername` method in `UrlRepository`.
     * This test verifies the behavior when URLs are fetched based on a username from the database.
     */
    @Test
    public void testFindAllUrlsByUsername_Success() {
        Url url1 = new Url();
        url1.setShortUrl("shortUrl1");
        url1.setLongUrl("http://example1.com");
        url1.setUser(testUser);

        Url url2 = new Url();
        url2.setShortUrl("shortUrl2");
        url2.setLongUrl("http://example2.com");
        url2.setUser(testUser);

        urlRepository.save(url1);
        urlRepository.save(url2);

        List<Url> result = urlRepository.findAllUrlsByUsername("testUser");

        assertEquals(2, result.size());
        assertEquals("shortUrl1", result.get(0).getShortUrl());
        assertEquals("http://example1.com", result.get(0).getLongUrl());
    }
}
