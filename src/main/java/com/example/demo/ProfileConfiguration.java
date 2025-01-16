package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfiguration {

    @Bean
    @Profile("default")
    public String defaultProfileConfig() {
        return "Налаштування для локальної розробки";
    }

    @Bean
    @Profile("prod")
    public String prodProfileConfig() {
        return "Налаштування для продукційного середовища";
    }
}

