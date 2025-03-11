package com.example.mapper;

import com.example.dto.rental.RentalPointDto;
import com.example.entity.RentalPoint;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


import java.util.List;

@Mapper(componentModel = "spring", uses = LocationMapper.class)
public interface RentalPointMapper {

    @Mapping(source = "parentPointId", target = "parentPoint.id")
    @Mapping(source = "location", target = "location", qualifiedByName = "mapDtoToLocation")
    RentalPoint dtoToEntity(RentalPointDto dto);

    @Mapping(source = "parentPoint.id", target = "parentPointId")
    @Mapping(source = "location", target = "location", qualifiedByName = "mapLocationToDto")
    RentalPointDto entityToDto(RentalPoint rentalPoint);

    List<RentalPointDto> toDtoList(List<RentalPoint> rentalPoints);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "parentPointId", target = "parentPoint.id")
    @Mapping(source = "location", target = "location", qualifiedByName = "mapDtoToLocation")
    void updateEntityFromDto(RentalPointDto dto, @MappingTarget RentalPoint entity);
}
