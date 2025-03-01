package org.senla.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.entity.User;
import org.senla.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    void testLoadUserByUsername() {
        User user = new User();
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        assertEquals(user, userDetailsService.loadUserByUsername("user"));
        verify(userRepository).findByUsername("user");
    }

    @Test
    void testLoadUserByUsernameThrowsException() {
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("user"));
    }
}
