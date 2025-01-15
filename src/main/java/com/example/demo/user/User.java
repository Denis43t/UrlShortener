package com.example.demo.user;

import com.example.demo.url.Url;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Entity class representing a User in the system.
 * This class is mapped to the "users" table in the "url_shortener" schema.
 * It contains fields to store user details such as username and password.
 *
 * <p> The class is annotated with {@link jakarta.persistence.Entity},
 * {@link jakarta.persistence.Table}, {@link jakarta.persistence.Id},
 * {@link jakarta.persistence.GeneratedValue}, {@link jakarta.persistence.GenerationType},
 * and {@link lombok.Data}, {@link lombok.AllArgsConstructor}, {@link lombok.NoArgsConstructor},
 * {@link lombok.Builder} to define the entity and automatically generate common methods. </p>
 *  <p> This class has a one-to-many relationship with the {@link Url} entity.
 *  * The {@code urls} field represents the set of URLs associated with the user.
 *  * This relationship will be fully implemented once the URL package is completed.
 *   <p> <b>//todo:</b> Field urls should be uncommented and connected to the appropriate URL mapping
 *  * once the {@link Url} package is fully implemented.</p>
 */
@Entity
@Table(schema = "url_shortener", name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    /**
     * The unique identifier for each user, automatically generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user, which must be unique and have a maximum length of 50 characters.
     */
    @Column(length = 50, nullable = false, unique = true)
    private String username;

    /**
     * The password of the user, which must have a maximum length of 100 characters.
     */
    @Column(length = 100, nullable = false)
    private String password;

    /**
     * A set of URLs associated with the user.
     * This field represents the one-to-many relationship between User and URLs.
     * It will be implemented once the URL package is completed.
     */
    //@OneToMany
    //@JoinColumn(name = "url_id")
    //public Set<Url> urls;
}