package com.example.demo.url;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url>findUrlByShortUrl(String shortUrl);

    @Query(value = "SELECT u FROM Url u WHERE u.user.username = :username")
    List<Url> findAllUrlsByUsername(@Param("username") String username);
}
