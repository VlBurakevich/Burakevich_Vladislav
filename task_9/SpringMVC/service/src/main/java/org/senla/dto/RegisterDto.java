package org.senla.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @NotBlank(message = "Имя пользователя не должно быть пустым")
    private String username;
    @Email(message = "Некорректный email адрес")
    private String email;
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
    @NotBlank(message = "Подтвердите пароль")
    private String confirmPassword;

}
