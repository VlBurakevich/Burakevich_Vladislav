package com.example.mapper;

import com.example.dto.location.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Named("mapLocationToDto")
    default LocationDto mapLocationToDto(String location) {
        if (location == null || location.isEmpty()) {
            return null;
        }

        String[] parts = location.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid location format. Expected 'latitude,longitude'.");
        }

        try {
            Double latitude = Double.parseDouble(parts[0].trim());
            Double longitude = Double.parseDouble(parts[1].trim());
            return new LocationDto(latitude, longitude);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in location string.");
        }
    }

    @Named("mapDtoToLocation")
    default String mapDtoToLocation(LocationDto locationDto) {
        if (locationDto == null) {
            return null;
        }
        return locationDto.getLatitude() + "," + locationDto.getLongitude();
    }
}
