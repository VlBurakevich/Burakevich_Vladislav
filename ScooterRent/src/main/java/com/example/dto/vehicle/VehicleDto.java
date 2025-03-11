package com.example.dto.vehicle;

import com.example.enums.VehiclesStatusEnum;
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
    private Long modelId;
    private String serialNumber;
    private int batteryLevel;
    private VehiclesStatusEnum status;
    private int mileage;
    private Long rentalPointId;
}
