package com.myapp.SpringJWT_Security.security;

import com.myapp.SpringJWT_Security.entity.User;
import com.myapp.SpringJWT_Security.model.Role;
import com.myapp.SpringJWT_Security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomUserDetailsServiceTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword(bCryptPasswordEncoder.encode("password"));
        user.setEmail("testuser@gmail.com");
        user.setRole(Role.USER);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
    }

    @Test
    @DisplayName("Load user by username")
    void loadUserByUsername() {
        String username = "testuser";

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));
    }
}