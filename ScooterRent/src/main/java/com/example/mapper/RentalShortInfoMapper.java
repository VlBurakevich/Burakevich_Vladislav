package com.example.mapper;

import com.example.dto.rental.RentalShortInfoDto;
import com.example.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RentalShortInfoMapper {

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "totalCost", source = "rentalCost.totalCost")
    @Mapping(target = "vehicleModelName", source = "vehicle.model.modelName")
    RentalShortInfoDto entityToDto(Rental rental);
}
