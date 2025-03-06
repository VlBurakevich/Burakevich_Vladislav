package com.example.mapper;

import com.example.dto.TransportTypeDto;
import com.example.entity.TransportType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransportTypeMapper {
    TransportTypeMapper INSTANCE = Mappers.getMapper(TransportTypeMapper.class);

    TransportType dtoToEntity(TransportTypeDto dto);

    TransportTypeDto entityToDto(TransportType entity);

    void updateEntityFromDto(TransportTypeDto dto, TransportType entity);
}
