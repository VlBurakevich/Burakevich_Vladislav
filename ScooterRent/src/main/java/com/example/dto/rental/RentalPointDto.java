package com.example.dto.rental;

import com.example.dto.location.LocationDto;
import com.example.enums.PointTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalPointDto {
    private Long id;
    private String pointName;
    private LocationDto location;
    private Integer capacity;
    private PointTypeEnum pointType;
    private Long parentPointId;
}
