package com.example.mapper;

import com.example.dto.ModelDto;
import com.example.entity.Model;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ModelMapper {
    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);

    Model dtoToEntity(ModelDto dto);

    ModelDto entityToDto(Model model);

    void updateEntityFromDto(ModelDto dto, Model model);
}
