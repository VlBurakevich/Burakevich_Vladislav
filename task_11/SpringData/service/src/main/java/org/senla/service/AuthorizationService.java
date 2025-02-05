package org.senla.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.dto.LoginDto;
import org.senla.dto.RegisterDto;
import org.senla.entity.Credential;
import org.senla.entity.User;
import org.senla.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class AuthorizationService {

    private final UserRepository userRepository;

    public boolean isValidUser(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername()).orElse(null);
        return user != null && user.getCredential().getPassword().equals(loginDto.getPassword());
    }

    public void validateRegistration(RegisterDto registerDto) {

        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }
    }

    @Transactional
    public void register(RegisterDto registerDto) {
        User newUser = new User();
        newUser.setUsername(registerDto.getUsername());

        Credential credential = new Credential();
        credential.setPassword(registerDto.getPassword());
        credential.setEmail(registerDto.getEmail());

        newUser.setCredential(credential);
        userRepository.save(newUser);
    }
}
