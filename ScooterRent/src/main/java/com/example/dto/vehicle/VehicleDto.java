package com.example.dto.vehicle;

import com.example.enums.VehiclesStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {
    private Long id;
    @NotNull(message = "modelId cannot be null")
    private Long modelId;
    @NotBlank(message = "serialNumber cannot be null")
    private String serialNumber;
    @NotNull(message = "batteryLevel cannot be null")
    private int batteryLevel;
    @NotNull(message = "status cannot be null")
    private VehiclesStatusEnum status;
    @NotNull(message = "mileage cannot be null")
    private int mileage;
    @NotNull(message = "rentalPointId cannot be null")
    private Long rentalPointId;
}
