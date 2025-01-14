package com.myapp.SpringJWT_Security.controller;

import com.myapp.SpringJWT_Security.entity.User;
import com.myapp.SpringJWT_Security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/api/login")
    public String login() {
        return "Hello World!";
    }

    @PostMapping("/api/register")
    public String register(@RequestBody User user) {
        userRepository.save(user);
        return "User was registered!";
    }

}
