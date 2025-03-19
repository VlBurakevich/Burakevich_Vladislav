package com.example.mapper;

import com.example.dto.finance.TarifDto;
import com.example.entity.Tarif;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TarifMapper {

    @Mapping(target = "transportType", ignore = true)
    Tarif dtoToEntity(TarifDto dto);

    @Mapping(source = "transportType.id", target = "transportTypeId")
    TarifDto entityToDto(Tarif tarif);

    @Mapping(target = "transportType", ignore = true)
    void updateEntityFromDto(TarifDto tarifDto, @MappingTarget Tarif existingTarif);
}
