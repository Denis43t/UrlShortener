package com.example.demo.v2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller handling requests for version 2 of the API.
 *
 * This controller provides endpoints specific to version 2, which might include features or information
 * related to ongoing development or planned improvements. The response format for all the version 2 endpoints
 * follows a similar structure, returning a `Map` containing a key-value message.
 */
@RestController
@RequestMapping("/api/v2/")
public class V2Controller {

    /**
     * Handles all requests under the `/api/v2/**` path.
     *
     * This method captures all requests under the version 2 of the API, whether they use GET, POST, PUT, DELETE, etc.
     * It returns a basic message indicating that version 2 is under development.
     *
     * @return a ResponseEntity containing a Map with a message key.
     */
    @RequestMapping(path = "/**")
    public ResponseEntity<Map<String, String>> handleAllRequests() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Version 2 is under development.");
        return ResponseEntity.ok(response);
    }
}