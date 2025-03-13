package com.example.dto.vehicle;

import com.example.dto.rental.RentalShortInfoDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "serialNumber cannot be null")
    private String serialNumber;
    @NotBlank(message = "modelName cannot be null")
    private String modelName;
    @NotNull(message = "batteryLevel cannot be null")
    private int batteryLevel;
    @NotNull(message = "mileage cannot be null")
    private int mileage;
    private List<RentalShortInfoDto> rentals;
}
