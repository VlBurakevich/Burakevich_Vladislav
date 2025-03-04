package com.example.service;

import com.example.dto.LoginDto;
import com.example.dto.RegisterDto;
import com.example.enitity.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import lombok.AllArgsConstructor;
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
            throw new
        }
    }

}
