package com.example.controller;

import com.example.dto.user.UserLoginDto;
import com.example.dto.user.UserRegisterDto;
import com.example.dto.user.UserLongInfoDto;
import com.example.dto.user.UserShortInfoDto;
import com.example.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<List<UserShortInfoDto>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.getUsers(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserLongInfoDto> getUserInfo(
            @PathVariable Long id
    ) {
        return userService.getUserInfo(id);
    }

    @PostMapping("/login")
    public ResponseEntity<String> processLogin(@Valid @RequestBody UserLoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PostMapping("/register")
    public ResponseEntity<String> processRegistration(@Valid @RequestBody UserRegisterDto registerDto) {
        return userService.register(registerDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRegisterDto> updateUserInfo(
            @PathVariable Long id,
            @Valid @RequestBody UserRegisterDto registerDto
    ) {
        return userService.updateUser(id, registerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/{id}/balance/top-up")
    public ResponseEntity<String> topUpBalance(
            @PathVariable Long id,
            @RequestParam BigDecimal amount
    ) {
        return userService.topUpBalance(id, amount);
    }
}
