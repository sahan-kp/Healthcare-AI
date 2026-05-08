package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    private UserService service;

    // CREATE
    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        return service.saveUser(user);
    }

    // READ
    @GetMapping("/all")
    public List<User> getAll(){
        return service.getAllUsers();
    }

    // UPDATE
    @PostMapping("/update")
    public User updateUser(@RequestBody User user){
        return service.saveUser(user);
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){

        service.deleteUser(id);

        return "Deleted";
    }

    // SEARCH
    @GetMapping("/search/{text}")
    public List<User> searchUser(@PathVariable String text){

        return service.searchUser(text);

    }
}