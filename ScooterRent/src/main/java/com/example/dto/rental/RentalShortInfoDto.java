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
public class RentalShortInfoDto {
    private Long id;
    private String username;
    private BigDecimal totalCost;
    private String vehicleModelName;
    private LocalDateTime rentalDate;
}
