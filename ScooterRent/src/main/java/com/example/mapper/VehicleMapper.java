package com.example.mapper;

import com.example.dto.vehicle.VehicleDto;
import com.example.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);

    Vehicle dtoToEntity(VehicleDto dto);

    VehicleDto entityToDto(Vehicle entity);

    void updateEntityFromDto(VehicleDto dto, Vehicle entity);
}
