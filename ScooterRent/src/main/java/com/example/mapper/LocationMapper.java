package com.example.mapper;

import com.example.dto.location.CoordinateDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.mapstruct.Mapper;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Named("mapCoordinateToDto")
    default CoordinateDto mapLocationToDto(JsonNode location) {
        if (location == null || !location.has("latitude") || !location.has("longitude")) {
            return null;
        }

        try {
            double latitude = location.get("latitude").asDouble();
            double longitude = location.get("longitude").asDouble();
            return new CoordinateDto(latitude, longitude);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid location JSON format.", e);
        }
    }

    @Named("mapDtoToCoordinate")
    default JsonNode mapDtoToLocation(CoordinateDto locationDto) {
        if (locationDto == null) {
            return null;
        }

        ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
        jsonNode.set("latitude", JsonNodeFactory.instance.numberNode(locationDto.getLatitude()));
        jsonNode.set("longitude", JsonNodeFactory.instance.numberNode(locationDto.getLongitude()));
        return jsonNode;
    }
}
