package com.example.demo.repository;

import com.example.demo.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository
        extends JpaRepository<Staff, Long> {

    // login check
    Optional<Staff> findByUsernameAndPasswordAndRole(
            String username,
            String password,
            String role
    );

    // username check
    Staff findByUsername(String username);

}