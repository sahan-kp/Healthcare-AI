package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.demo.response.ApiResponse;
@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    private UserService service;

    // CREATE USER
    @PostMapping("/add")
    public ApiResponse addUser(@Valid @RequestBody UserDTO dto){

        service.saveUser(dto);

        return new ApiResponse(

                true,
                "User Added Successfully"

        );

    }
    // GET ALL USERS
    @GetMapping("/all")
    public List<User> getAll(){

        return service.getAllUsers();

    }

    // UPDATE USER
    @PutMapping("/update/{id}")
    public ApiResponse updateUser(

            @PathVariable Long id,

            @Valid @RequestBody UserDTO dto

    ){

        service.updateUser(id, dto);

        return new ApiResponse(

                true,
                "User Updated Successfully"

        );

    }

    // DELETE USER
    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteUser(@PathVariable Long id){

        service.deleteUser(id);

        return new ApiResponse(

                true,
                "User Deleted Successfully"

        );

    }

    // SEARCH USER
    @GetMapping("/search/{text}")
    public List<User> searchUser(@PathVariable String text){

        return service.searchUser(text);

    }
}