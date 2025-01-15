package com.example.demo;


import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up an embedded PostgreSQL database for testing.
 *
 * <p>This configuration uses the `EmbeddedPostgres` library to create and start
 * an embedded PostgreSQL instance on a specified port (15432). It initializes
 * the database before running any tests and provides logging in case of any
 * failures during the setup.
 */
@Configuration
@Slf4j
public class PostgresTestConfig {

    static {
        try {
            EmbeddedPostgres.builder()
                    .setPort(15432)
                    .start();
        } catch (Exception e) {
            log.atError().log(e.getMessage());
        }
    }
}
