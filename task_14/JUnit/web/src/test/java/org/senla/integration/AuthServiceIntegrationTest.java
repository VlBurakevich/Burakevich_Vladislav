package org.senla.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.senla.dto.LoginDto;
import org.senla.dto.RegisterDto;
import org.senla.entity.User;
import org.senla.repository.UserRepository;
import org.senla.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    private RegisterDto registerDto;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        registerDto = new RegisterDto();
        registerDto.setUsername("testUser");
        registerDto.setPassword("password123");
        registerDto.setConfirmPassword("password123");
        registerDto.setEmail("testuser@example.com");
    }

    @Test
    void testRegisterUser() {
        authService.register(registerDto);
        User registeredUser = userRepository.findByUsername("testUser").orElse(null);

        assertNotNull(registeredUser);
        assertEquals("testUser", registeredUser.getUsername());
        assertEquals("testuser@example.com", registeredUser.getCredential().getEmail());
    }

    @Test
    void testValidUserLogin() {
        authService.register(registerDto);
        LoginDto loginDto = new LoginDto("testUser", "password123");

        assertTrue(authService.isValidUser(loginDto));
    }

    @Test
    void testInvalidUserLogin() {
        LoginDto loginDto = new LoginDto("nonexistent", "wrongPassword");
        assertFalse(authService.isValidUser(loginDto));
    }
}
