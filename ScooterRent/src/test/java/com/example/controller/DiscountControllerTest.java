package com.example.controller;

import com.example.dto.finance.DiscountDto;
import com.example.dto.finance.DiscountListDto;
import com.example.enums.DiscountTypeEnum;
import com.example.service.DiscountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountControllerTest {

    @InjectMocks
    private DiscountController discountController;

    @Mock
    private DiscountService discountService;

    @Test
    void testGetDiscounts() {
        DiscountDto discountDto = new DiscountDto(1L, "Discount", DiscountTypeEnum.PERCENTAGE, new BigDecimal("10.00"));
        List<DiscountDto> discountList = Collections.singletonList(discountDto);
        DiscountListDto discountListDto = new DiscountListDto(discountList);

        when(discountService.getDiscounts(0, 10)).thenReturn(discountListDto);

        DiscountListDto response = discountController.getDiscounts(0, 10);

        assertEquals(discountListDto, response);
        verify(discountService, times(1)).getDiscounts(0, 10);
    }

    @Test
    void testCreateDiscount() {
        DiscountDto discountDto = new DiscountDto(null, "Discount", DiscountTypeEnum.FIXED, new BigDecimal("15.00"));
        DiscountDto savedDiscountDto = new DiscountDto(1L, "Discount", DiscountTypeEnum.FIXED, new BigDecimal("15.00"));

        when(discountService.createDiscount(discountDto)).thenReturn(savedDiscountDto);

        DiscountDto response = discountController.createDiscount(discountDto);

        assertEquals(savedDiscountDto, response);
        verify(discountService, times(1)).createDiscount(discountDto);
    }

    @Test
    void testUpdateDiscount() {
        Long discountId = 1L;
        DiscountDto discountDto = new DiscountDto(discountId, "Updated Discount", DiscountTypeEnum.PERCENTAGE, new BigDecimal("20.00"));

        when(discountService.updateDiscount(discountId, discountDto)).thenReturn(discountDto);

        DiscountDto response = discountController.updateDiscount(discountId, discountDto);

        assertEquals(discountDto, response);
        verify(discountService, times(1)).updateDiscount(discountId, discountDto);
    }

    @Test
    void testDeleteDiscount() {
        Long discountId = 1L;

        discountController.deleteDiscount(discountId);

        verify(discountService, times(1)).deleteDiscount(discountId);
    }
}
