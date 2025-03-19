package com.example.mapper;

import com.example.dto.vehicle.TransportTypeDto;
import com.example.entity.TransportType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransportTypeMapper {

    TransportType dtoToEntity(TransportTypeDto dto);

    TransportTypeDto entityToDto(TransportType entity);

    void updateEntityFromDto(TransportTypeDto dto, @MappingTarget TransportType entity);
}
