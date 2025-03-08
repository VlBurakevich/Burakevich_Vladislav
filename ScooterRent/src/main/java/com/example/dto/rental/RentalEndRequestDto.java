package com.example.dto.rental;

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
    private Long rentalId;
    private Long endPointId;
    private LocalDateTime endTime;
    private int batteryLevel;
}
