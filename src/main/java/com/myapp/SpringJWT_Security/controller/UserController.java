package com.myapp.SpringJWT_Security.controller;

import com.myapp.SpringJWT_Security.entity.User;
import com.myapp.SpringJWT_Security.repository.UserRepository;
import com.myapp.SpringJWT_Security.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> login() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .toList();

            String jwtToken = jwtTokenProvider.generateToken(username, roles);
            String jwtRefreshToken = jwtTokenProvider.generateRefreshToken(username, roles);

            System.out.println(username);
            System.out.println(roles);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", jwtToken);
            tokens.put("refreshToken", jwtRefreshToken);

            System.out.println("JWT Token: " + jwtToken);

            return ResponseEntity.ok(tokens);

    }

    @PostMapping("/api/register")
    public String register(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User was registered!";
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null) {
            return ResponseEntity.ok("No user is logged in!");
        }

        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .toList();

        User user = userRepository.findByUsername(username);

        Map<String, Object> userDetails = new HashMap<>();

        userDetails.put("username", user.getUsername());
        userDetails.put("email", user.getEmail());
        userDetails.put("roles", roles);

        return ResponseEntity.ok(userDetails);
    }

}
