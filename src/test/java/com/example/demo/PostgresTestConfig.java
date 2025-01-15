package com.example.demo;


import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration class for setting up an embedded PostgreSQL database for testing.
 *
 * <p>This configuration uses the `EmbeddedPostgres` library to create and start
 * an embedded PostgreSQL instance on a specified port (15432). It initializes
 * the database before running any tests and provides logging in case of any
 * failures during the setup.
 */
@Profile("dev")
@Configuration
@Slf4j
public class PostgresTestConfig {

    /**
     * Static field represents an embedded instance of a PostgreSQL database
     * used specifically for testing purposes.
     */
    private static EmbeddedPostgres postgres;

    static {
        try {
            postgres = EmbeddedPostgres.builder()
                    .setPort(15432)
                    .start();
        } catch (Exception e) {
            log.atError().log(e.getMessage());
        }
    }
}
