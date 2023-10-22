package com.example.tutorial.AOPAndValidation.dtos;

import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

@Validated
public class User {

    @NotNull
    private int id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 20, message = "Name should be minimum 3 characters and maximum 20 characters")
    private String name;

    @NotNull
    @Email
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}