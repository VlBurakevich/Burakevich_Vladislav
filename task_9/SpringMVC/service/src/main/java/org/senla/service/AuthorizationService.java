package org.senla.service;

import lombok.AllArgsConstructor;
import org.senla.dto.LoginDto;
import org.senla.dto.RegisterDto;
import org.senla.entity.Credential;
import org.senla.entity.User;
import org.senla.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorizationService {

    private final UserRepository userRepository;

    public boolean isValidUser(LoginDto loginDto) {
        User user = userRepository.getByUsername(loginDto.getUsername());
        return user != null && user.getCredential().getPassword().equals(loginDto.getPassword());
    }

    public void register(RegisterDto registerDto) {
        User newUser = new User();
        newUser.setUsername(registerDto.getUsername());

        Credential credential = new Credential();
        credential.setPassword(registerDto.getPassword());
        credential.setEmail(registerDto.getEmail());

        newUser.setCredential(credential);

        userRepository.save(newUser);
    }

    public List<String> validateRegistration(RegisterDto registerDto) {
        List<String> errors = new ArrayList<>();

        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            errors.add("Password do not match");
        }

        if (userRepository.isUsernameExists(registerDto.getUsername())) {
            errors.add("Username is already taken");
        }

        return errors;
    }
}
