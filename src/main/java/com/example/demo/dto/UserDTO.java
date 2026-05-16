package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "Age is required")
    private Integer age;

    private Integer heartRate;

    private String bp;

    private String sugar;

    private String bmi;

    private String symptoms;
}