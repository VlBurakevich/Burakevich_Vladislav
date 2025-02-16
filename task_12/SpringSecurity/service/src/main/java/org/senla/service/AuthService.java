package org.senla.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public boolean isValidUser(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername()).orElse(null);
        return user != null && passwordEncoder.matches(loginDto.getPassword(), user.getCredential().getPassword());
    }

    public void validateRegistration(RegisterDto registerDto) {
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new ValidationException("Passwords do not match");
        }
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new ValidationException("Username is not unique");
        }
    }

    @Transactional
    public void register(RegisterDto registerDto) {
        User newUser = new User();
        newUser.setUsername(registerDto.getUsername());

        Role userRole = roleRepository.findByName(Role.USER).orElseThrow(() -> new DatabaseGetException(Role.class.getSimpleName()));
        newUser.addRole(userRole);

        Credential credential = new Credential();
        credential.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        credential.setEmail(registerDto.getEmail());

        newUser.setCredential(credential);
        userRepository.save(newUser);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return user;
        }
        throw new AuthenticationException();
    }

    public Long getAuthenticatedUserId() {
        return getAuthenticatedUser().getId();
    }
}
