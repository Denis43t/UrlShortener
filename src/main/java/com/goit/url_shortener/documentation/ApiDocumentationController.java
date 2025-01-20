package com.goit.url_shortener.documentation;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api")
public class ApiDocumentationController {

    /**
     * Returns the content of the stable version of the API documentation.
     *
     * @return JSON content of the openapiV1.json file.
     */
    @GetMapping("/docs")
    public ResponseEntity<String> getApiStableDocumentation() throws IOException {
        return serveFile("static/documentation/openapiV1.json");
    }

    /**
     * Returns the content of version 1 of the API documentation.
     *
     * @return JSON content of the openapiV1.json file.
     */
    @GetMapping({"/v1/docs", "/v1/docs/openapiV1.json"})
    public ResponseEntity<String> getApiV1Documentation() throws IOException {
        return serveFile("static/documentation/openapiV1.json");
    }

    /**
     * Returns the content of version 2 of the API documentation.
     *
     * @return JSON content of the openapiV2.json file.
     */
    @GetMapping({"/v2/docs", "/v2/docs/openapiV2.json"})
    public ResponseEntity<String> getApiV2Documentation() throws IOException {
        return serveFile("static/documentation/openapiV2.json");
    }

    /**
     * Reads a JSON file from the classpath and returns its content as a response.
     *
     * @param filePath path to the JSON file in the classpath.
     * @return ResponseEntity with JSON content.
     */
    private ResponseEntity<String> serveFile(String filePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);

        String content = Files.readString(resource.getFile().toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}