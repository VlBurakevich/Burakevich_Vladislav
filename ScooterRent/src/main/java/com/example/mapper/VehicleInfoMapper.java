package com.example.mapper;

import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.vehicle.VehicleInfoDto;
import com.example.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleInfoMapper {
    VehicleInfoMapper INSTANCE = Mappers.getMapper(VehicleInfoMapper.class);

    VehicleInfoDto toVehicleInfoDto(Vehicle vehicle, List<RentalShortInfoDto> rentals);
}
