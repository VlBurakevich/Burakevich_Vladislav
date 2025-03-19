package com.example.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserShortInfoDto {

    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;

    @NotBlank(message = "username cannot be empty")
    @Schema(description = "Имя пользователя", example = "user123")
    private String username;

    @NotBlank(message = "balance cannot be empty")
    @Schema(description = "Баланс пользователя", example = "5000.00")
    private BigDecimal balance;
}
