package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    // SAVE
    public User saveUser(User user){
        return repo.save(user);
    }

    // GET ALL
    public List<User> getAllUsers(){
        return repo.findAll();
    }

    // DELETE
    public void deleteUser(Long id){
        repo.deleteById(id);
    }

    // SEARCH
    public List<User> searchUser(String text){
        return repo.findAll()
                .stream()
                .filter(u ->
                        u.getName().toLowerCase().contains(text.toLowerCase())
                                ||
                                u.getEmail().toLowerCase().contains(text.toLowerCase())
                )
                .toList();
    }
}