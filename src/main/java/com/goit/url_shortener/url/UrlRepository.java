package com.goit.url_shortener.url;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The `UrlRepository` interface is a repository layer for performing CRUD operations on `Url` entities.
 * It extends the `JpaRepository` interface, which provides basic methods for interacting with a database.
 * Additionally, custom query methods are defined to fetch URLs based on specific criteria.
 *
 * The repository is annotated with `@Repository` to indicate that it is a Spring Data JPA repository.
 */
@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    /**
     * Finds an `Url` entity by its shortened URL.
     *
     * This method returns an `Optional<Url>`, which may contain a URL if found, or be empty if not.
     *
     * @param shortUrl The shortened URL to search for.
     * @return An `Optional` containing the found `Url` entity, or empty if not found.
     */
    Optional<Url> findUrlByShortUrl(String shortUrl);

    /**
     * Finds all `Url` entities associated with a specific username.
     *
     * This custom query uses JPQL (Java Persistence Query Language) to retrieve URLs where
     * the `username` matches the provided parameter.
     *
     * @param username The username whose URLs need to be fetched.
     * @return A list of `Url` entities that belong to the specified username.
     */
    @Query(value = "SELECT u FROM Url u WHERE u.user.username = :username")
    List<Url> findAllUrlsByUsername(@Param("username") String username);
}
