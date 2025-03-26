package com.example.service;

import com.example.dto.finance.DiscountDto;
import com.example.dto.finance.DiscountListDto;
import com.example.entity.Discount;
import com.example.enums.DiscountTypeEnum;
import com.example.exceptions.CreateException;
import com.example.exceptions.DeleteException;
import com.example.exceptions.UpdateException;
import com.example.mapper.DiscountMapper;
import com.example.repository.DiscountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    @InjectMocks
    private DiscountService discountService;

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private DiscountMapper discountMapper;

    @Test
    void testGetDiscounts() {
        Discount discount = new Discount(1L, "Test Discount", DiscountTypeEnum.PERCENTAGE, BigDecimal.TEN);
        DiscountDto discountDto = new DiscountDto(1L, "Test Discount", DiscountTypeEnum.PERCENTAGE, BigDecimal.TEN);
        Page<Discount> discountPage = new PageImpl<>(Collections.singletonList(discount));

        when(discountRepository.findAll(any(PageRequest.class))).thenReturn(discountPage);
        when(discountMapper.entityToDto(discount)).thenReturn(discountDto);

        DiscountListDto result = discountService.getDiscounts(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getDiscounts().size());
        assertEquals(discountDto, result.getDiscounts().getFirst());

        verify(discountRepository, times(1)).findAll(any(PageRequest.class));
        verify(discountMapper, times(1)).entityToDto(discount);
    }

    @Test
    void testCreateDiscount_Success() {
        DiscountDto discountDto = new DiscountDto(null, "Test Discount", DiscountTypeEnum.PERCENTAGE, BigDecimal.TEN);
        Discount discount = new Discount(null, "Test Discount", DiscountTypeEnum.PERCENTAGE, BigDecimal.TEN);
        Discount savedDiscount = new Discount(1L, "Test Discount", DiscountTypeEnum.PERCENTAGE, BigDecimal.TEN);
        DiscountDto savedDiscountDto = new DiscountDto(1L, "Test Discount", DiscountTypeEnum.PERCENTAGE, BigDecimal.TEN);

        when(discountRepository.existsByName(discountDto.getName())).thenReturn(false);
        when(discountMapper.dtoToEntity(discountDto)).thenReturn(discount);
        when(discountRepository.save(discount)).thenReturn(savedDiscount);
        when(discountMapper.entityToDto(savedDiscount)).thenReturn(savedDiscountDto);

        DiscountDto result = discountService.createDiscount(discountDto);

        assertNotNull(result);
        assertEquals(savedDiscountDto, result);

        verify(discountRepository, times(1)).existsByName(discountDto.getName());
        verify(discountRepository, times(1)).save(discount);
    }

    @Test
    void testCreateDiscount_AlreadyExists() {
        DiscountDto discountDto = new DiscountDto(null, "Test Discount", DiscountTypeEnum.PERCENTAGE, BigDecimal.TEN);
        when(discountRepository.existsByName(discountDto.getName())).thenReturn(true);

        assertThrows(CreateException.class, () -> discountService.createDiscount(discountDto));
        verify(discountRepository, times(1)).existsByName(discountDto.getName());
        verify(discountRepository, never()).save(any());
    }

    @Test
    void testUpdateDiscount_Success() {
        Long id = 1L;
        DiscountDto discountDto = new DiscountDto(id, "Updated Discount", DiscountTypeEnum.FIXED, BigDecimal.valueOf(20));
        Discount existingDiscount = new Discount(id, "Old Discount", DiscountTypeEnum.PERCENTAGE, BigDecimal.TEN);

        when(discountRepository.findById(id)).thenReturn(Optional.of(existingDiscount));
        when(discountRepository.save(existingDiscount)).thenReturn(existingDiscount);
        when(discountMapper.entityToDto(existingDiscount)).thenReturn(discountDto);

        DiscountDto result = discountService.updateDiscount(id, discountDto);

        assertNotNull(result);
        assertEquals(discountDto, result);

        verify(discountRepository, times(1)).findById(id);
        verify(discountRepository, times(1)).save(existingDiscount);
    }

    @Test
    void testUpdateDiscount_NotFound() {
        Long id = 1L;
        DiscountDto discountDto = new DiscountDto(id, "Updated Discount", DiscountTypeEnum.FIXED, BigDecimal.valueOf(20));

        when(discountRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UpdateException.class, () -> discountService.updateDiscount(id, discountDto));
        verify(discountRepository, times(1)).findById(id);
        verify(discountRepository, never()).save(any());
    }

    @Test
    void testDeleteDiscount_Success() {
        Long id = 1L;
        when(discountRepository.existsById(id)).thenReturn(true);

        discountService.deleteDiscount(id);

        verify(discountRepository, times(1)).existsById(id);
        verify(discountRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteDiscount_NotFound() {
        Long id = 1L;
        when(discountRepository.existsById(id)).thenReturn(false);

        assertThrows(DeleteException.class, () -> discountService.deleteDiscount(id));
        verify(discountRepository, times(1)).existsById(id);
        verify(discountRepository, never()).deleteById(any());
    }
}
