package com.example.dto.rental;

import com.example.dto.location.LocationDto;
import com.example.enums.PointTypeEnum;
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
public class RentalPointDto {
    private Long id;
    @NotBlank(message = "pointName cannot be empty")
    private String pointName;
    @NotNull(message = "location cannot be null")
    private LocationDto location;
    @NotNull(message = "capacity cannot be null")
    private Integer capacity;
    @NotNull(message = "pointType cannot be null")
    private PointTypeEnum pointType;
    @NotNull(message = "parentPointId cannot be null")
    private Long parentPointId;
}
