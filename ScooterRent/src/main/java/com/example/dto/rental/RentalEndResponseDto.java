package com.example.dto.rental;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalEndResponseDto {

    @NotNull(message = "startTime cannot be null")
    @Schema(description = "Время начала аренды", example = "2025-03-14T17:38:48.219Z")
    private LocalDateTime startTime;

    @NotNull(message = "endTime cannot be null")
    @Schema(description = "Время окончания аренды", example = "2025-03-14T18:38:48.219Z")
    private LocalDateTime endTime;

    @NotNull(message = "tarifName cannot be null")
    @Schema(description = "Название тарифа", example = "Стандартный")
    private String tarifName;

    @NotNull(message = "discountName cannot be null")
    @Schema(description = "Название скидки", example = "Скидка 10%")
    private String discountName;

    @NotNull(message = "rentalPrice cannot be null")
    @Schema(description = "Стоимость аренды", example = "1500.00")
    private BigDecimal rentalPrice;
}
