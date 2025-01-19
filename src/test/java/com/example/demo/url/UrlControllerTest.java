package com.example.demo.url;

import com.example.demo.url.dto.UrlRequest;
import com.example.demo.url.dto.UrlResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the UrlController class.
 *
 * This class contains test cases to verify the functionality of the UrlController methods.
 * It checks both the success scenarios for shortening, expanding, updating, and deleting URLs.
 */
public class UrlControllerTest {

    // Mocked dependencies for the UrlService and UrlController
    private final UrlService urlService = Mockito.mock(UrlService.class);
    private final UrlController urlController = new UrlController(urlService);

    /**
     * Tests the successful creation of a shortened URL.
     *
     * <p> This test verifies that when a valid UrlRequest is passed, the shortFromLong method
     * of UrlService returns a successful UrlResponse. The response is then checked to ensure
     * it matches the expected outcome with the correct status code and body.
     */
    @Test
    public void testShortFromLong_Success() {
        UrlRequest request = new UrlRequest();
        request.setUrl("http://example.com");
        UrlResponse expectedResponse = new UrlResponse();
        expectedResponse.setShortUrl("http://short.ly/abc123");
        expectedResponse.setStatus(HttpStatus.OK);

        when(urlService.getShortUrlFromLongUrl(any(UrlRequest.class))).thenReturn(expectedResponse);

        ResponseEntity<UrlResponse> responseEntity = urlController.shortFromLong(request, "auth-token");

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    /**
     * Tests the successful expansion of a shortened URL.
     *
     * <p> This test verifies that when a valid UrlRequest is passed, the longFromShort method
     * of UrlService returns a successful UrlResponse. The response is then checked to ensure
     * it matches the expected outcome with the correct status code and body.
     */
    @Test
    public void testLongFromShort_Success() {
        UrlRequest request = new UrlRequest();
        request.setUrl("http://short.ly/abc123");
        UrlResponse expectedResponse = new UrlResponse();
        expectedResponse.setLongUrl("http://example.com");
        expectedResponse.setStatus(HttpStatus.OK);

        when(urlService.getLongUrlFromShortUrl(any(UrlRequest.class))).thenReturn(expectedResponse);

        ResponseEntity<UrlResponse> responseEntity = urlController.longFromShort(request);

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    /**
     * Tests the successful update of a URL.
     *
     * <p> This test checks that when a valid UrlRequest is passed to the updateUrl method,
     * it returns a successful UrlResponse. The response is verified by comparing the status code
     * and body to the expected result.
     */
    @Test
    public void testUpdateUrl_Success() {
        UrlRequest request = new UrlRequest();
        request.setUrl("http://example.com");
        UrlResponse expectedResponse = new UrlResponse();
        expectedResponse.setStatus(HttpStatus.OK);

        when(urlService.updateUrl(any(UrlRequest.class))).thenReturn(expectedResponse);

        ResponseEntity<UrlResponse> responseEntity = urlController.updateUrl(request, "auth-token");

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    /**
     * Tests the successful deletion of a URL.
     *
     * <p> This test ensures that when a valid UrlRequest is passed to the deleteUrl method,
     * the service returns a response with the expected status code.
     */
    @Test
    public void testDeleteUrl_Success() {
        UrlRequest request = new UrlRequest();
        request.setUrl("http://short.ly/abc123");
        UrlResponse expectedResponse = new UrlResponse();
        expectedResponse.setStatus(HttpStatus.OK);

        when(urlService.deleteUrl(any(UrlRequest.class))).thenReturn(expectedResponse);

        ResponseEntity<UrlResponse> responseEntity = urlController.deleteUrl(request, "auth-token");

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}
