package com.example.dto.rental;

import com.example.dto.location.LocationDto;
import com.example.dto.vehicle.VehicleDto;
import com.example.enums.PointTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalPointInfoDto {
    private Long id;
    private String pointName;
    private LocationDto location;
    private PointTypeEnum pointType;
    private List<VehicleDto> vehicle;
}
