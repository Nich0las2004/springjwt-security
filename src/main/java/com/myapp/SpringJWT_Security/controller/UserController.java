package com.myapp.SpringJWT_Security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("api/login")
    public String login() {
        return "Hello World!";
    }

    @PostMapping("api/register")
    public String register() {
        return "Hello World!";
    }

}
