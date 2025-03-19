package com.example.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {

    @NotBlank(message = "Username cannot be empty")
    @Schema(description = "Имя пользователя", example = "user123")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Schema(description = "Пароль", example = "password123")
    private String password;
}
