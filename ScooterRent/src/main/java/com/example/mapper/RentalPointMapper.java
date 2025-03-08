package com.example.mapper;

import com.example.dto.rental.RentalPointDto;
import com.example.entity.RentalPoint;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RentalPointMapper {
    RentalPointMapper INSTANCE = Mappers.getMapper(RentalPointMapper.class);

    RentalPoint dtoToEntity(RentalPointDto dto);

    RentalPointDto entityToDto(RentalPoint entity);

    void updateEntityFromDto(RentalPointDto dto, RentalPoint entity);
}
