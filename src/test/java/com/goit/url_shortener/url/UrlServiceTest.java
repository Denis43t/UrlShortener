package com.goit.url_shortener.url;

import com.goit.url_shortener.security.AuthorizationService;
import com.goit.url_shortener.url.dto.UrlRequest;
import com.goit.url_shortener.url.dto.UrlResponse;
import com.goit.url_shortener.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.goit.url_shortener.util.MessageProvider.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the UrlService class.
 *
 * This class contains test cases to verify the behavior of the `UrlService`,
 * including functionality for generating short URLs from long URLs, retrieving long URLs from short URLs,
 * updating existing URLs, and deleting URLs.
 * The tests utilize mocking for dependencies such as `UrlRepository`, `AuthorizationService`, `LongUrlValidator`,
 * and `ShortUrlGenerator`.
 */
@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @InjectMocks
    private UrlServiceImpl urlService;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private LongUrlValidator validator;

    @Mock
    private ShortUrlGenerator urlGenerator;


    /**
     * Test for `getShortUrlFromLongUrl` method in `UrlServiceImpl`.
     * This test verifies the behavior when a valid long URL is provided and the user is authorized.
     */
    @Test
    public void testGetShortUrlFromLongUrl_Success() {
        UrlRequest request = new UrlRequest();
        request.setUrl("http://example.com");
        request.setAuthorizationHeader("Bearer validToken");

        User user = new User();
        user.setUsername("testUser");

        when(authorizationService.getAuthorizedUser("Bearer validToken")).thenReturn(Optional.of(user));
        when(validator.isValid("http://example.com")).thenReturn(true);
        when(urlGenerator.generateShortUrl()).thenReturn("shortUrl123");

        UrlResponse response = urlService.getShortUrlFromLongUrl(request);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals("shortUrl123", response.getShortUrl());
        assertEquals("http://example.com", response.getLongUrl());
    }

    /**
     * Test for `getShortUrlFromLongUrl` method in `UrlServiceImpl`.
     * This test verifies the behavior when the long URL is invalid.
     */
    @Test
    public void testGetShortUrlFromLongUrl_InvalidUrl() {
        UrlRequest request = new UrlRequest();
        request.setUrl("invalidUrl");
        request.setAuthorizationHeader("Bearer validToken");

        User user = new User();
        user.setUsername("testUser");

        when(authorizationService.getAuthorizedUser("Bearer validToken")).thenReturn(Optional.of(user));
        when(validator.isValid("invalidUrl")).thenReturn(false);

        UrlResponse response = urlService.getShortUrlFromLongUrl(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        assertEquals(INCORRECT_URL_MESSAGE, response.getMessage());
    }

    /**
     * Test for `getLongUrlFromShortUrl` method in `UrlServiceImpl`.
     * This test verifies the behavior when a valid shortened URL is provided.
     */
    @Test
    public void testGetLongUrlFromShortUrl_Success() {
        UrlRequest request = new UrlRequest();
        request.setUrl("shortUrl123");

        Url url = new Url();
        url.setLongUrl("http://example.com");
        url.setExpiresAt(LocalDateTime.now().plusDays(1));

        when(urlRepository.findUrlByShortUrl("shortUrl123")).thenReturn(Optional.of(url));

        UrlResponse response = urlService.getLongUrlFromShortUrl(request);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("http://example.com", response.getLongUrl());
    }

    /**
     * Test for `getLongUrlFromShortUrl` method in `UrlServiceImpl`.
     * This test verifies the behavior when the shortened URL is expired.
     */
    @Test
    public void testGetLongUrlFromShortUrl_ExpiredUrl() {
        UrlRequest request = new UrlRequest();
        request.setUrl("shortUrl123");

        Url url = new Url();
        url.setLongUrl("http://example.com");
        url.setExpiresAt(LocalDateTime.now().minusDays(1));

        when(urlRepository.findUrlByShortUrl("shortUrl123")).thenReturn(Optional.of(url));

        UrlResponse response = urlService.getLongUrlFromShortUrl(request);

        assertEquals(HttpStatus.GONE, response.getStatus());
        assertEquals(EXPIRED_URL_MESSAGE, response.getMessage());
    }

    /**
     * Test for `updateUrl` method in `UrlServiceImpl`.
     * This test verifies the behavior when a valid request to update the URL is provided.
     */
    @Test
    public void testUpdateUrl_Success() {
        UrlRequest request = new UrlRequest();
        request.setUrl("shortUrl123");
        request.setAuthorizationHeader("Bearer validToken");

        User user = new User();
        user.setUsername("testUser");

        Url url = new Url();
        url.setShortUrl("shortUrl123");
        url.setLongUrl("http://example.com");

        when(authorizationService.getAuthorizedUser("Bearer validToken")).thenReturn(Optional.of(user));
        when(urlRepository.findUrlByShortUrl("shortUrl123")).thenReturn(Optional.of(url));
        when(urlGenerator.generateShortUrl()).thenReturn("newShortUrl456");

        UrlResponse response = urlService.updateUrl(request);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("newShortUrl456", response.getShortUrl());
        assertEquals("http://example.com", response.getLongUrl());
    }

    /**
     * Test for `deleteUrl` method in `UrlServiceImpl`.
     * This test verifies the behavior when a valid request to delete the URL is provided.
     */
    @Test
    public void testDeleteUrl_Success() {
        UrlRequest request = new UrlRequest();
        request.setUrl("shortUrl123");
        request.setAuthorizationHeader("Bearer validToken");

        User user = new User();
        user.setUsername("testUser");

        Url url = new Url();
        url.setShortUrl("shortUrl123");
        url.setLongUrl("http://example.com");

        when(authorizationService.getAuthorizedUser("Bearer validToken")).thenReturn(Optional.of(user));
        when(urlRepository.findUrlByShortUrl("shortUrl123")).thenReturn(Optional.of(url));

        UrlResponse response = urlService.deleteUrl(request);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(URL_DELETED_MESSAGE, response.getMessage());
    }
}