package com.example.mapper;

import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.vehicle.VehicleInfoDto;
import com.example.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleInfoMapper {

    @Mapping(source = "vehicle.model.modelName", target = "modelName")
    VehicleInfoDto toVehicleInfoDto(Vehicle vehicle, List<RentalShortInfoDto> rentals);
}
