package com.example.controller;

import com.example.dto.user.UserLoginDto;
import com.example.dto.user.UserLongInfoDto;
import com.example.dto.user.UserRegisterDto;
import com.example.dto.user.UserShortInfoListDto;
import com.example.service.UserService;
import com.example.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/")
@Tag(name = "User API", description = "Управление пользователями")
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Получить список пользователей", description = "Возвращает список пользователей с пагинацией.")
    public UserShortInfoListDto getUsers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return userService.getUsers(page, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Получить информацию о пользователе", description = "Возвращает подробную информацию о пользователе по его идентификатору.")
    public UserLongInfoDto getUserInfo(
            @PathVariable Long id
    ) {
        return userService.getUserInfo(id);
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Получить информацию о текущем пользователе", description = "Возвращает подробную информацию о текущем пользователе.")
    public UserLongInfoDto getCurrentUserInfo() {
        return userService.getCurrentUserInfo();
    }

    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    @Operation(summary = "Авторизация пользователя", description = "Авторизует пользователя.")
    public String processLogin(@Valid @RequestBody UserLoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    @Operation(summary = "Регистрация пользователя", description = "Регистрирует нового пользователя.")
    public String processRegistration(@Valid @RequestBody UserRegisterDto registerDto) {
        return userService.register(registerDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Обновить информацию о пользователе", description = "Обновляет информацию о пользователе по его идентификатору.")
    public UserRegisterDto updateUserInfo(
            @PathVariable Long id,
            @Valid @RequestBody UserRegisterDto registerDto
    ) {
        return userService.updateUser(id, registerDto);
    }

    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Обновить информацию о текущем пользователе", description = "Обновляет информацию о текущем пользователе")
    public UserRegisterDto updateCurrentUserInfo(@Valid @RequestBody UserRegisterDto registerDto) {
        return userService.updateUser(AuthUtil.getAuthenticatedUserId(), registerDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по его идентификатору.")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{id}/balance/top-up")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Пополнить баланс", description = "Пополняет баланс пользователя.")
    public String topUpBalance(
            @PathVariable Long id,
            @RequestParam BigDecimal amount
    ) {
        return userService.topUpBalance(id, amount);
    }
}
