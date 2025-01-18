package com.myapp.SpringJWT_Security.controller;

import com.myapp.SpringJWT_Security.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final JwtTokenProvider jwtTokenProvider;

    public TokenController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("Authorization") String authorizationHeader) {
        String refreshToken = authorizationHeader.substring(7);

        if (jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.extractUsername(refreshToken);
            List<String> roles = jwtTokenProvider.extractRoles(refreshToken);

            String newAccessToken = jwtTokenProvider.generateToken(username, roles);

            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token.");
        }

    }

}
