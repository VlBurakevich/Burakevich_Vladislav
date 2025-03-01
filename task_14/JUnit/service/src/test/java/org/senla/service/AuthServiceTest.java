package org.senla.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.dto.LoginDto;
import org.senla.dto.RegisterDto;
import org.senla.entity.Credential;
import org.senla.entity.Role;
import org.senla.entity.User;
import org.senla.exceptions.AuthenticationException;
import org.senla.exceptions.DatabaseGetException;
import org.senla.exceptions.ValidationException;
import org.senla.repository.RoleRepository;
import org.senla.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("username");

        Credential credential = new Credential();
        credential.setPassword("encodedPassword");
        credential.setEmail("test@example.com");

        user.setCredential(credential);
    }

    @Test
    void testIsValidUser_ValidCredentials() {
        LoginDto loginDto = new LoginDto("testUser", "password");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        assertTrue(authService.isValidUser(loginDto));
    }

    @Test
    void testIsValidUser_InvalidCredentials() {
        LoginDto loginDto = new LoginDto("testUser", "wrongPassword");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        assertFalse(authService.isValidUser(loginDto));
    }

    @Test
    void testValidateRegistration_PasswordMismatch() {
        RegisterDto registerDto = new RegisterDto("newUser", "password", "wrongPassword", "test@example.com");

        assertThrows(ValidationException.class, () -> authService.validateRegistration(registerDto));
    }

    @Test
    void testValidateRegistration_UsernameNotUnique() {
        RegisterDto registerDto = new RegisterDto("newUser", "password", "password", "test@example.com");

        when(roleRepository.findByName(Role.USER)).thenReturn(Optional.empty());

        assertThrows(DatabaseGetException.class, () -> authService.register(registerDto));
    }

    @Test
    void testRegister_Success() {
        RegisterDto registerDto = new RegisterDto("newUser", "password", "password", "test@example.com");

        Role role = new Role();
        role.setName(Role.USER);
        role.setUsers(new ArrayList<>());

        when(roleRepository.findByName(Role.USER)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> authService.register(registerDto));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetAuthenticatedUser_Success() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);

        User authenticatedUser = authService.getAuthenticatedUser();
        assertEquals(user, authenticatedUser);
    }

    @Test
    void testGetAuthenticatedUser_ThrowsException() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("anonymousUser");

        assertThrows(AuthenticationException.class, () -> authService.getAuthenticatedUser());
    }

    @Test
    void testGetAuthenticatedUserId_Success() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);

        Long userId = authService.getAuthenticatedUserId();
        assertEquals(1L, userId);
    }
}