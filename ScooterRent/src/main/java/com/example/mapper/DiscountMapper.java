package com.example.mapper;

import com.example.dto.finance.DiscountDto;
import com.example.entity.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

    @Mapping(target = "type", source = "discountType")
    @Mapping(target = "value", source = "discountAmount")
    Discount dtoToEntity(DiscountDto discountDto);

    @Mapping(target = "discountType", source = "type")
    @Mapping(target = "discountAmount", source = "value")
    DiscountDto entityToDto(Discount discount);

    @Mapping(target = "type", source = "discountType")
    @Mapping(target = "value", source = "discountAmount")
    void updateEntityFromDto(DiscountDto discountDto, @MappingTarget Discount discount);
}
