package com.example.dto.user;

import com.example.dto.rental.RentalShortInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLongInfoDto {

    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;

    @NotBlank(message = "username cannot be empty")
    @Schema(description = "Имя пользователя", example = "user123")
    private String username;

    @NotNull(message = "balance cannot be null")
    @Schema(description = "Баланс пользователя", example = "5000.00")
    private BigDecimal balance;

    @Schema(description = "Список краткой информации об арендах")
    private List<RentalShortInfoDto> rentalShortInfoDtos;
}
