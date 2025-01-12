package com.example.demo.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {


    boolean existsByName(String name);
}
