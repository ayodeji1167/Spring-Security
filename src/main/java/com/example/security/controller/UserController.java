package com.example.security.controller;


import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {


    @GetMapping("/admin")
    @Async
    public String admin() {


        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication);


        return "Welcome Admin";
    }

}