package com.example.dto.rental;

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
public class RentalEndResponseDto {
    @NotNull(message = "startTime cannot be null")
    private LocalDateTime startTime;
    @NotNull(message = "endTime cannot be null")
    private LocalDateTime endTime;
    @NotNull(message = "tarifName cannot be null")
    private String tarifName;
    @NotNull(message = "discountName cannot be null")
    private String discountName;
    @NotNull(message = "rentalPrice cannot be null")
    private BigDecimal rentalPrice;
}
