package com.example.tutorial.AOPAndValidation.services;

import com.example.tutorial.AOPAndValidation.annotations.Log;
import com.example.tutorial.AOPAndValidation.dtos.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    @Log
    public List<User> getAll() {
        return users;
    }

    @Log
    public User getById(int id) {
        return users.get(id-1);
    }

    public List<User> addUser(User user) {
        users.add(user);
        return users;
    }

    public List<User> updateUser(int id, User user) {
        users.set(id-1, user);
        return users;
    }

    public List<User> deleteUser(int id) {
        User user = users.get(id-1);
        users.remove(id-1);
        return users;
    }
}
