package org.senla.controller.authorization;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.senla.dto.RegisterDto;
import org.senla.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/authorization")
public class RegisterController {
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> processRegistration(@Valid @RequestBody RegisterDto registerDto) {
        authService.validateRegistration(registerDto);
        authService.register(registerDto);

        return ResponseEntity.ok("Registration successful");
    }
}
