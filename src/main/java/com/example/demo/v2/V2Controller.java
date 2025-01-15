package com.example.demo.v2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/v2")
public class V2Controller {

    /**
     * Handles GET requests to the base URL of version 2.
     *
     * <p> This endpoint responds with a simple message indicating that version 2
     * is under development.
     *
     * @return A ResponseEntity containing a map with a key "message" and a value
     * indicating the current status of version 2.
     */
    @GetMapping
    public ResponseEntity<Map<String, String>> get() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Version 2 is under development");
        return ResponseEntity.ok(response);
    }
}

