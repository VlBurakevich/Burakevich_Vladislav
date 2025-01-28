package org.senla.controller.authorization;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.dto.RegisterDto;
import org.senla.service.AuthorizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/authorization")
public class RegisterController {
    private AuthorizationService authorizationService;

    @PostMapping("/register")
    public ResponseEntity<String> processRegistration(@Valid @RequestBody RegisterDto registerDto) {
        log.info("Registration attempt for username: {}", registerDto.getUsername());

        List<String> errors = authorizationService.validateRegistration(registerDto);

        if (!errors.isEmpty()) {
            log.warn("Registration failed for username: {}. Errors: {}", registerDto.getUsername(), errors);
            return ResponseEntity.badRequest().body(errors.toString());
        }

        authorizationService.register(registerDto);
        log.info("Registration successful for username: {}", registerDto.getUsername());

        return ResponseEntity.ok("Registration successful");
    }

}
