package org.senla.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.senla.entity.Credential;
import org.senla.entity.User;
import org.senla.repository.UserRepository;
import org.senla.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class CustomUserDetailsServiceIntegrationTest {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        User user = new User();
        user.setUsername("testUser");

        Credential credential = new Credential();
        credential.setEmail("test@test.com");
        credential.setPassword("password");
        user.setCredential(credential);
        userRepository.save(user);

        UserDetails loadedUser = userDetailsService.loadUserByUsername("testUser");

        assertNotNull(loadedUser);
        assertEquals("testUser", loadedUser.getUsername());
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername("nonexistent"));
    }
}
