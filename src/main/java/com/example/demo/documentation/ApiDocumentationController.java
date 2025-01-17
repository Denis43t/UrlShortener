package com.example.demo.documentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * In this application is using documentation from yaml file for supporting the principle of cline code.
 *  That's why for getting api documentation is used Api documentation controller.
 */
@Controller
@RequestMapping("**")
public class ApiDocumentationController {

    /**
     * Redirects to the stable version of the API documentation.
     *
     * @return a redirect URL pointing to the stable OpenAPI documentation (openapiV1.json).
     */
    @GetMapping("/api/docs")
    public String getApiStableDocumentation() {
        return "redirect:/documentation/openapiV1.json";
    }

    /**
     * Redirects to the version 1 of the API documentation.
     *
     * @return a redirect URL pointing to OpenAPI documentation for version 1 (openapiV1.json).
     */
    @GetMapping({"/api/v1/docs", "/openapiV1.json"})
    public String getApiV1Documentation() {
        return "redirect:/documentation/openapiV1.json";
    }

    /**
     * Redirects to the version 2 of the API documentation.
     *
     * @return a redirect URL pointing to OpenAPI documentation for version 2 (openapiV2.json).
     */
    @GetMapping({"/api/v2/docs", "/openapiV2.json"})
    public String getApiV2Documentation() {
        return "redirect:/documentation/openapiV2.json";
    }
}