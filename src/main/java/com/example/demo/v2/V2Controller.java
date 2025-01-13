package com.example.demo.v2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v2")
public class V2Controller {

    @GetMapping
    public ResponseEntity<Map<String, String>> get() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Version 2 is under development");
        return ResponseEntity.ok(response);
    }
}
