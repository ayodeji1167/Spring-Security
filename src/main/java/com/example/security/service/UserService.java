package com.example.security.service;

import com.example.security.entity.User;

public interface UserService {
    User addNewUser(User user);
    void deleteUserById(int id);
}
