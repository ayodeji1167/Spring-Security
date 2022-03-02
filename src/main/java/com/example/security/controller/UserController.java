package com.example.security.controller;


import com.example.security.service.UserServiceImmplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserServiceImmplementation userServiceImmplementation;

    @GetMapping("/")
    public String home() {
        return "Welcome home";
    }


    @GetMapping("/login")
    public String user() {

        return "Welcome To The Login Page";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Welcome Admin";
    }

}