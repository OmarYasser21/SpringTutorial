package com.example.tutorial.CenterManagement.services;

import com.example.tutorial.CenterManagement.repositories.InstructorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorValidation {
    private final InstructorRepo instructorRepo;


    @Autowired
    public InstructorValidation(InstructorRepo instructorRepo){
        this.instructorRepo = instructorRepo;
    }

    protected boolean isPhoneNumberUnique(String phoneNumber) {
        return instructorRepo.findByPhoneNumber(phoneNumber) == null;
    }

    protected boolean isEmailValid(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
