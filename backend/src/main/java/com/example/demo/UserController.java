package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserRepository repo;

    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        return repo.save(user);
    }

    @GetMapping("/all")
    public List<User> getAll(){
        return repo.findAll();
    }
}
