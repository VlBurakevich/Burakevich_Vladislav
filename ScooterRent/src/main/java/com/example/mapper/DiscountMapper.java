package com.example.mapper;

import com.example.dto.DiscountDto;
import com.example.entity.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
    DiscountMapper INSTANCE = Mappers.getMapper(DiscountMapper.class);

    Discount dtoToEntity(DiscountDto discountDto);

    DiscountDto entityTiDto(Discount discount);

    void updateEntityFromDto(DiscountDto discountDto,Discount discount);
}
