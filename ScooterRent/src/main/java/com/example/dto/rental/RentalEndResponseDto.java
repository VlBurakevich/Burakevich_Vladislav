package com.example.dto.rental;

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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String tarifName;
    private String discountName;
    private BigDecimal rentalPrice;
}
