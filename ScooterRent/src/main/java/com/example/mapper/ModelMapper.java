package com.example.mapper;

import com.example.dto.vehicle.ModelDto;
import com.example.entity.Model;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    @Mapping(source = "model", target = "modelName")
    @Mapping(target = "transportType", ignore = true)
    Model dtoToEntity(ModelDto dto);

    @Mapping(source = "modelName", target = "model")
    @Mapping(source = "transportType.id", target = "transportTypeId")
    ModelDto entityToDto(Model model);

    @Mapping(source = "model", target = "modelName")
    @Mapping(target = "transportType", ignore = true)
    void updateEntityFromDto(ModelDto dto, @MappingTarget Model model);
}
