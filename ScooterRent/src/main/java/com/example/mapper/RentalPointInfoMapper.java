package com.example.mapper;

import com.example.dto.rental.RentalPointInfoDto;
import com.example.entity.RentalPoint;
import com.example.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {LocationMapper.class, VehicleMapper.class})
public interface RentalPointInfoMapper {

    @Mapping(source = "rentalPoint.location", target = "location", qualifiedByName = "mapLocationToDto")
    RentalPointInfoDto toRentalPointInfoDto(RentalPoint rentalPoint, List<Vehicle> vehicles);

}
