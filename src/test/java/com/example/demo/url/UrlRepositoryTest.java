package com.example.demo.url;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

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
     * Initializes the database and ensures each test starts with a clean slate.
     */
    @BeforeEach
    void setUp() {
        // Clear existing data before each test
        urlRepository.deleteAll();
        userRepository.deleteAll();

        // Create a test user and save it
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
