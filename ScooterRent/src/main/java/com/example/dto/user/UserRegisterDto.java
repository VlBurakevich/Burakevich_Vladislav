package com.example.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    @NotBlank(message = "Username cannot be empty")
    @Schema(description = "Имя пользователя", example = "user123")
    private String username;

    @Email(message = "Invalid email address")
    @Schema(description = "Электронная почта", example = "user@example.com")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Schema(description = "Пароль", example = "password123")
    private String password;

    @NotBlank(message = "Confirm password cannot be empty")
    @Schema(description = "Подтверждение пароля", example = "password123")
    private String confirmPassword;
}