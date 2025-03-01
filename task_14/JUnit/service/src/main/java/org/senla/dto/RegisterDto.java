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
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    @NotBlank(message = "Confirm password cannot be empty")
    private String confirmPassword;
}
