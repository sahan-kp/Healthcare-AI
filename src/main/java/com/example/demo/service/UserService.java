package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    // SAVE USER
    public User saveUser(UserDTO dto){

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setHeartRate(dto.getHeartRate());
        user.setBp(dto.getBp());
        user.setSugar(dto.getSugar());
        user.setBmi(dto.getBmi());
        user.setSymptoms(dto.getSymptoms());

        return repo.save(user);
    }

    // UPDATE USER
    public User updateUser(Long id, UserDTO dto){

        User user = repo.findById(id).orElseThrow();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setHeartRate(dto.getHeartRate());
        user.setBp(dto.getBp());
        user.setSugar(dto.getSugar());
        user.setBmi(dto.getBmi());
        user.setSymptoms(dto.getSymptoms());

        return repo.save(user);
    }

    // GET ALL USERS
    public List<User> getAllUsers(){

        return repo.findAll();

    }

    // DELETE USER
    public void deleteUser(Long id){

        repo.deleteById(id);

    }

    // SEARCH USER
    public List<User> searchUser(String text){

        return repo.findAll()
                .stream()
                .filter(user ->

                        user.getName().toLowerCase().contains(text.toLowerCase())

                                ||

                                user.getEmail().toLowerCase().contains(text.toLowerCase())

                )
                .toList();
    }
}