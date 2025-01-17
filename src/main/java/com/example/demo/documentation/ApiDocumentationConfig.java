package com.example.demo.documentation;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiDocumentationConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/openapiV1.json")
                .addResourceLocations("classpath:/static/documentation/openapiV1.json")
                .setCachePeriod(3600)
                .resourceChain(true);

        registry.addResourceHandler("/openapiV2.json")
                .addResourceLocations("classpath:/static/documentation/openapiV2.json")
                .setCachePeriod(3600)
                .resourceChain(true);
    }
}