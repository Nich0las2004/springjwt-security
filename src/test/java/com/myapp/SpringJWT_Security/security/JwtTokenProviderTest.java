package com.myapp.SpringJWT_Security.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    @Test
    @DisplayName("Generate token")
    void generateToken() {
        String username = "testUser";
        List<String> roles = List.of("ROLE_USER");

        String token = JwtTokenProvider.generateToken(username, roles);

        assertNotNull(token, "Generated token should not be null");
        assertFalse(token.isEmpty(), "Generated token should not be empty");
    }

    @Test
    @DisplayName("Generate refresh token")
    void generateRefreshToken() {
        String username = "testUser";
        List<String> roles = List.of("ROLE_USER");

        String refreshToken = JwtTokenProvider.generateRefreshToken(username, roles);

        assertNotNull(refreshToken, "Generated refresh token should not be null");
        assertFalse(refreshToken.isEmpty(), "Generated refresh token should not be empty");
    }

    @Test
    @DisplayName("Extract username")
    void extractUsername() {
        String username = "testUser";
        List<String> roles = List.of("ROLE_USER");
        String token = JwtTokenProvider.generateToken(username, roles);

        String extractedUsername = JwtTokenProvider.extractUsername(token);

        assertEquals(username, extractedUsername, "Extracted username should match the original username");
    }

    @Test
    @DisplayName("Extract roles")
    void extractRoles() {
        String username = "testUser";
        List<String> roles = List.of("ROLE_USER", "ROLE_ADMIN");
        String token = JwtTokenProvider.generateToken(username, roles);

        List<String> extractedRoles = JwtTokenProvider.extractRoles(token);

        assertNotNull(extractedRoles, "Extracted roles should not be null");
        assertEquals(roles, extractedRoles, "Extracted roles should match the original roles");
    }

    @Test
    @DisplayName("Validate token")
    void validateToken() {
        String token = JwtTokenProvider.generateToken("user", List.of("ROLE_USER"));

        boolean isValid = JwtTokenProvider.validateToken(token, new TokenBlacklist());

        assertTrue(isValid, "Valid token should pass validation");
    }
}