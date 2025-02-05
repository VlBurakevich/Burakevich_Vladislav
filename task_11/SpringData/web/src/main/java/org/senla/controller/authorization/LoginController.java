package org.senla.controller.authorization;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.senla.dto.LoginDto;
import org.senla.service.AuthorizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/authorization")
public class LoginController {
    private AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<String> processLogin(@Valid @RequestBody LoginDto loginDto) {
        if (!authorizationService.isValidUser(loginDto)) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        return ResponseEntity.ok("Login successful");
    }
}
