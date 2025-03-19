package com.example.dto.rental;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalShortInfoDto {

    @Schema(description = "Идентификатор аренды", example = "1")
    private Long id;

    @NotBlank(message = "username cannot be null")
    @Schema(description = "Имя пользователя", example = "user123")
    private String username;

    @NotNull(message = "totalCost cannot be null")
    @Schema(description = "Общая стоимость аренды", example = "1500.00")
    private BigDecimal totalCost;

    @NotNull(message = "vehicleModelName cannot be null")
    @Schema(description = "Название модели транспортного средства", example = "Tesla Model S")
    private String vehicleModelName;

    @NotNull(message = "rentalTime cannot be null")
    @Schema(description = "Дата аренды", example = "2025-03-14T17:38:48.219Z")
    private LocalDateTime createdAt;
}
