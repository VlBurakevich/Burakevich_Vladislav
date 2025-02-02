package org.senla.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.dto.LoginDto;
import org.senla.dto.RegisterDto;
import org.senla.entity.Credential;
import org.senla.entity.User;
import org.senla.repository.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthorizationService {

    private final UserRepository userRepository;

    public boolean isValidUser(LoginDto loginDto) {
        log.info("Validating user: {}", loginDto.getUsername());
        User user = userRepository.getByUsername(loginDto.getUsername());
        boolean isValid = user != null && user.getCredential().getPassword().equals(loginDto.getPassword());
        log.debug("User validation result: {}", isValid);
        return isValid;
    }

    public void register(RegisterDto registerDto) {
        log.info("Registering user: {}", registerDto.getUsername());
        try {
            User newUser = new User();
            newUser.setUsername(registerDto.getUsername());

            Credential credential = new Credential();
            credential.setPassword(registerDto.getPassword());
            credential.setEmail(registerDto.getEmail());

            newUser.setCredential(credential);
            userRepository.save(newUser);

            log.info("User successfully registered: {}", registerDto.getUsername());
        } catch (Exception e) {
            log.error("Error during registration for user: {}", registerDto.getUsername(), e);
            throw e;
        }
    }

    public void validateRegistration(RegisterDto registerDto) {
        log.info("Validating registration for user: {}", registerDto.getUsername());

        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            log.warn("Password mismatch for user: {}", registerDto.getUsername());
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.isUsernameExists(registerDto.getUsername())) {
            log.warn("Username already exists: {}", registerDto.getUsername());
            throw new IllegalArgumentException("Username is already taken");
        }
    }
}
