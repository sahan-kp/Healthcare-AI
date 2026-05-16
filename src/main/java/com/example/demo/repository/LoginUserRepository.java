package com.example.demo.repository;

import com.example.demo.entity.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginUserRepository
        extends JpaRepository<LoginUser, Long> {

    // login check
    Optional<LoginUser> findByUsernameAndPasswordAndRole(
            String username,
            String password,
            String role
    );

    // username check
    LoginUser findByUsername(String username);

}