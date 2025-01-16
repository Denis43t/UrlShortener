package com.example.demo.documentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/")
public class ApiDocumentationController {

    @GetMapping("/v1/docs")
    public String getApiV1Documentation() {
        return "redirect:/documentation/openapiV1.json";
    }

    @GetMapping("/v2/docs")
    public String getApiV2Documentation() {
        return "redirect:/documentation/openapiV2.json";
    }
}