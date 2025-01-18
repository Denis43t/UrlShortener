package com.example.demo.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface representing the repository for managing {@link User} entities.
 * This interface extends the {@link CrudRepository}, which provides basic CRUD operations.
 *
 * <p> It also contains custom query methods to check the existence of a user by username
 * and to find a user by their username.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Checks if a user with the given username already exists in the database.
     *
     * @param username The username to check for existence.
     * @return {@code true} if a user with the given username exists, {@code false} otherwise.
     */
    boolean existsByUsername(String username);

    /**
     * Retrieves an {@link Optional} containing the user with the specified username, if found.
     *
     * @param username The username of the user to find.
     * @return An {@link Optional} containing the user, or empty if no user was found.
     */
    Optional<User> findByUsername(String username);
}
