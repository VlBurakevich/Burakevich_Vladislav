package com.example.mapper;

import com.example.dto.finance.DiscountDto;
import com.example.entity.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

    Discount dtoToEntity(DiscountDto discountDto);

    DiscountDto entityToDto(Discount discount);

    void updateEntityFromDto(DiscountDto discountDto, @MappingTarget Discount discount);
}
