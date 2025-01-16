package com.myapp.SpringJWT_Security.controller;

import com.myapp.SpringJWT_Security.entity.User;
import com.myapp.SpringJWT_Security.repository.UserRepository;
import com.myapp.SpringJWT_Security.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/api/login")
    public String login() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .toList();

            String jwtToken = jwtTokenProvider.generateToken(username, roles);

            System.out.println(username);
            System.out.println(roles);

            System.out.println("JWT Token: " + jwtToken);

            return "Welcome to the login page!";

    }

    @PostMapping("/api/register")
    public String register(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User was registered!";
    }

}
