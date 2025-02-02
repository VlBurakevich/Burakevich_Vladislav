package org.senla.controller.authorization;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.dto.LoginDto;
import org.senla.service.AuthorizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/authorization")
public class LoginController {
    private AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<String> processLogin(@Valid @RequestBody LoginDto loginDto) {
        log.info("Attempting login for username: {}", loginDto.getUsername());

        if (!authorizationService.isValidUser(loginDto)) {
            log.warn("Invalid login attempt for username: {}", loginDto.getUsername());
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        log.info("Login successful for username: {}", loginDto.getUsername());
        return ResponseEntity.ok("Login successful");
    }
}
