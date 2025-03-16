package com.example.controller;

import com.example.dto.finance.DiscountDto;
import com.example.enums.DiscountTypeEnum;
import com.example.service.DiscountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

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
        DiscountDto discountDto = new DiscountDto();
        discountDto.setId(1L);
        discountDto.setName("Discount");
        discountDto.setType(DiscountTypeEnum.PERCENTAGE);
        discountDto.setValue(new BigDecimal("10.00"));

        List<DiscountDto> discountList = Collections.singletonList(discountDto);

        when(discountService.getDiscounts(0, 10)).thenReturn(ResponseEntity.ok(discountList));

        ResponseEntity<List<DiscountDto>> response = discountController.getDiscounts(0, 10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(discountList, response.getBody());
        verify(discountService, times(1)).getDiscounts(0, 10);
    }

    @Test
    void testCreateDiscount() {
        DiscountDto discountDto = new DiscountDto();
        discountDto.setName("Discount");
        discountDto.setType(DiscountTypeEnum.FIXED);
        discountDto.setValue(new BigDecimal("15.00"));

        when(discountService.createDiscount(discountDto)).thenReturn(ResponseEntity.ok(discountDto));

        ResponseEntity<DiscountDto> response = discountController.createDiscount(discountDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(discountDto, response.getBody());
        verify(discountService, times(1)).createDiscount(discountDto);
    }

    @Test
    void testUpdateDiscount() {
        Long discountId = 1L;
        DiscountDto discountDto = new DiscountDto();
        discountDto.setName("Discount");
        discountDto.setType(DiscountTypeEnum.PERCENTAGE);
        discountDto.setValue(new BigDecimal("20.00"));

        when(discountService.updateDiscount(discountId, discountDto)).thenReturn(ResponseEntity.ok(discountDto));

        ResponseEntity<DiscountDto> response = discountController.updateDiscount(discountId, discountDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(discountDto, response.getBody());
        verify(discountService, times(1)).updateDiscount(discountId, discountDto);
    }

    @Test
    void testDeleteDiscount() {
        Long discountId = 1L;
        when(discountService.deleteDiscount(discountId)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = discountController.deleteDiscount(discountId);

        assertEquals(204, response.getStatusCode().value());
        verify(discountService, times(1)).deleteDiscount(discountId);
    }
}
