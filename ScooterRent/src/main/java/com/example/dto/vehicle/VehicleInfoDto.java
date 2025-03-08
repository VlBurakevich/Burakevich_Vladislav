package com.example.dto.vehicle;

import com.example.dto.rental.RentalShortInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfoDto {
    private Long id;
    private String serialNumber;
    private String modelName;
    private int batteryLevel;
    private int mileage;
    private List<RentalShortInfoDto> rentals;
}
