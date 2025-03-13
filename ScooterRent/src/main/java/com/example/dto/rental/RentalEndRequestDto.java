package com.example.dto.rental;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalEndRequestDto {
    @NotNull(message = "rentalId cannot be null")
    private Long rentalId;
    @NotNull(message = "endPointId cannot be null")
    private Long endPointId;
    @NotNull(message = "endTime cannot be null")
    private LocalDateTime endTime;
    @NotNull(message = "batteryLevel cannot be null")
    private int batteryLevel;
}
