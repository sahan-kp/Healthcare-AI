package com.example.demo.controller;

import com.example.demo.entity.LoginUser;
import com.example.demo.repository.LoginUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LoginUserRepository repo;

    // =========================
    // CREATE USER
    // =========================
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(
            @RequestBody LoginUser user){

        // username duplicate check
        LoginUser existingUser =
                repo.findByUsername(user.getUsername());

        if(existingUser != null){

            return ResponseEntity
                    .badRequest()
                    .body("Username already exists");

        }

        LoginUser savedUser = repo.save(user);

        return ResponseEntity.ok(savedUser);
    }

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginUser user){

        Optional<LoginUser> foundUser =

                repo.findByUsernameAndPasswordAndRole(

                        user.getUsername(),
                        user.getPassword(),
                        user.getRole()

                );

        if(foundUser.isPresent()){

            return ResponseEntity.ok(foundUser.get());

        }

        else{

            return ResponseEntity
                    .badRequest()
                    .body("INVALID USERNAME OR PASSWORD");

        }

    }

    // =========================
    // GET ALL USERS
    // =========================
    @GetMapping("/login-users")
    public List<LoginUser> getAll(){

        return repo.findAll();

    }

    // =========================
    // DELETE USER
    // =========================
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id){

        try{

            repo.deleteById(id);

            return ResponseEntity.ok("USER DELETED");

        }

        catch(Exception e){

            return ResponseEntity
                    .badRequest()
                    .body("DELETE FAILED");

        }

    }

}