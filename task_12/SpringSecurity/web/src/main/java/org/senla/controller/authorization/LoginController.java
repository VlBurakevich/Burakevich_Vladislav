package org.senla.controller.authorization;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.senla.dto.LoginDto;
import org.senla.security.JwtService;
import org.senla.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/authorization")
public class LoginController {
    private AuthService authService;
    private JwtService jwtService;

    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<String> processLogin(@Valid @RequestBody LoginDto loginDto) {
        if (!authService.isValidUser(loginDto)) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
        String token = jwtService.generateToken(loginDto.getUsername());
        return ResponseEntity.ok(token);
    }
}
