package org.senla.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegisterDto {
    private String username;
    private String email;
    private String password;
}
