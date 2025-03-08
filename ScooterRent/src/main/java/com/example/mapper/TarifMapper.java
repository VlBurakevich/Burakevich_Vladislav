package com.example.mapper;

import com.example.dto.finance.TarifDto;
import com.example.entity.Tarif;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TarifMapper {
    TarifMapper INSTANCE = Mappers.getMapper(TarifMapper.class);

    Tarif dtoToEntity(TarifDto dto);

    TarifDto entityToDto(Tarif tarif);

    void updateEntityFromDto(TarifDto tarifDto, Tarif existingTarif);
}
