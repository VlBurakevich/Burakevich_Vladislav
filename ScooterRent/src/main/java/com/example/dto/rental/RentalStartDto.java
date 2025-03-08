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
public class RentalStartDto {
    private Long vehicleId;
    private Long startPointId;
    private LocalDateTime startTime;
    private Long tarifId;
    private Long discountId;
}
