package com.example.mapper;

import com.example.dto.vehicle.VehicleDto;
import com.example.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(source = "modelId", target = "model.id")
    @Mapping(source = "rentalPointId", target = "rentalPoint.id")
    Vehicle dtoToEntity(VehicleDto dto);

    @Mapping(source = "model.id", target = "modelId")
    @Mapping(source = "rentalPoint.id", target = "rentalPointId")
    VehicleDto entityToDto(Vehicle entity);

    @Mapping(source = "modelId", target = "model.id", ignore = true)
    @Mapping(source = "rentalPointId", target = "rentalPoint.id", ignore = true)
    void updateEntityFromDto(VehicleDto dto, @MappingTarget Vehicle entity);
}
