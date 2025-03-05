package com.example.controller;

import com.example.dto.LoginDto;
import com.example.dto.RegisterDto;
import com.example.dto.UserShortInfoDto;
import com.example.security.JwtService;
import com.example.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users/")
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserShortInfoDto>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getUsers(page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserShortInfoDto> getUser(@PathVariable Long id) {
        //TODO
    }


    @PostMapping("/{id}/balance/top-up")
    public ResponseEntity<String> topUpBalance(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        return userService.topUpBalance(id, amount);
    }

    @PostMapping("/login")
    public ResponseEntity<String> processLogin(@Valid @RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PostMapping("/register")
    public ResponseEntity<String> processRegistration(@Valid @RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);
    }
}
