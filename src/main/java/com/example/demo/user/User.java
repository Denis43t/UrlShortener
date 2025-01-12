package com.example.demo.user;

import jakarta.persistence.*;

@Entity
@Table(schema = "usr_shortener", name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Column(length = 100, nullable = false)
    private String password;
}
