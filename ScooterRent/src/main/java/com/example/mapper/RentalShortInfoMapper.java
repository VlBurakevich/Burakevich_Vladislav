package com.example.mapper;

import com.example.dto.rental.RentalShortInfoDto;
import com.example.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RentalShortInfoMapper {
    RentalShortInfoMapper INSTANCE = Mappers.getMapper(RentalShortInfoMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "totalCost", source = "rentalCost.totalCost")
    @Mapping(target = "vehicleModelName", source = "vehicle.model.modelName")
    @Mapping(target = "rentalDate", source = "createdAt")
    RentalShortInfoDto entityToDto(Rental rental);
}
