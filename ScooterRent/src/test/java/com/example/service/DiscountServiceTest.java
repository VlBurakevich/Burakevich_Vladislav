package com.example.service;

import com.example.dto.finance.DiscountDto;
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
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Discount discount = new Discount();
        discount.setId(1L);
        discount.setName("Test Discount");
        discount.setType(DiscountTypeEnum.PERCENTAGE);
        discount.setValue(BigDecimal.valueOf(10.0));

        DiscountDto discountDto = new DiscountDto();
        discountDto.setId(1L);
        discountDto.setName("Test Discount");
        discountDto.setType(DiscountTypeEnum.PERCENTAGE);
        discountDto.setValue(BigDecimal.valueOf(10.0));

        Page<Discount> discountPage = new PageImpl<>(Collections.singletonList(discount));
        when(discountRepository.findAll(any(PageRequest.class))).thenReturn(discountPage);
        when(discountMapper.entityToDto(discount)).thenReturn(discountDto);

        ResponseEntity<List<DiscountDto>> response = discountService.getDiscounts(0, 10);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(discountDto, response.getBody().getFirst());

        verify(discountRepository, times(1)).findAll(any(PageRequest.class));
        verify(discountMapper, times(1)).entityToDto(discount);
    }

    @Test
    void testCreateDiscount_Success() {
        DiscountDto discountDto = new DiscountDto();
        discountDto.setName("Test Discount");
        discountDto.setType(DiscountTypeEnum.PERCENTAGE);
        discountDto.setValue(BigDecimal.valueOf(10.0));

        Discount discount = new Discount();
        discount.setName("Test Discount");
        discount.setType(DiscountTypeEnum.PERCENTAGE);
        discount.setValue(BigDecimal.valueOf(10.0));

        when(discountRepository.existsByName(discountDto.getName())).thenReturn(false);
        when(discountMapper.dtoToEntity(discountDto)).thenReturn(discount);
        when(discountRepository.save(discount)).thenReturn(discount);
        when(discountMapper.entityToDto(discount)).thenReturn(discountDto);

        ResponseEntity<DiscountDto> response = discountService.createDiscount(discountDto);

        assertNotNull(response.getBody());
        assertEquals(discountDto, response.getBody());

        verify(discountRepository, times(1)).existsByName(discountDto.getName());
        verify(discountRepository, times(1)).save(discount);
        verify(discountMapper, times(1)).dtoToEntity(discountDto);
        verify(discountMapper, times(1)).entityToDto(discount);
    }

    @Test
    void testUpdateDiscount_AlreadyExist() {
        DiscountDto discountDto = new DiscountDto();
        discountDto.setName("Test Discount");
        discountDto.setType(DiscountTypeEnum.PERCENTAGE);
        discountDto.setValue(BigDecimal.valueOf(10.0));

        when(discountRepository.existsByName(discountDto.getName())).thenReturn(true);

        CreateException exception = assertThrows(CreateException.class, () -> discountService.createDiscount(discountDto));
        assertTrue(exception.getMessage().contains(Discount.class.getSimpleName()));

        verify(discountRepository, times(1)).existsByName(discountDto.getName());
        verify(discountRepository, never()).save(any());
    }

    @Test
    void testUpdateDiscount_Success() {
        Long id = 1L;
        DiscountDto discountDto = new DiscountDto();
        discountDto.setId(id);
        discountDto.setName("Updated Discount");
        discountDto.setType(DiscountTypeEnum.FIXED);
        discountDto.setValue(BigDecimal.valueOf(20.0));

        Discount existingDiscount = new Discount();
        existingDiscount.setId(id);
        existingDiscount.setName("Old Discount");
        existingDiscount.setType(DiscountTypeEnum.PERCENTAGE);
        existingDiscount.setValue(BigDecimal.valueOf(10.0));

        when(discountRepository.findById(id)).thenReturn(Optional.of(existingDiscount));
        when(discountRepository.save(existingDiscount)).thenReturn(existingDiscount);
        when(discountMapper.entityToDto(existingDiscount)).thenReturn(discountDto);

        ResponseEntity<DiscountDto> response = discountService.updateDiscount(id, discountDto);

        assertNotNull(response.getBody());
        assertEquals(discountDto, response.getBody());

        verify(discountRepository, times(1)).findById(id);
        verify(discountRepository, times(1)).save(existingDiscount);
        verify(discountMapper, times(1)).entityToDto(existingDiscount);
    }

    @Test
    void testUpdateDiscount_NotFound() {
        Long id = 1L;
        DiscountDto discountDto = new DiscountDto();
        discountDto.setId(id);
        discountDto.setName("Updated Discount");
        discountDto.setType(DiscountTypeEnum.FIXED);
        discountDto.setValue(BigDecimal.valueOf(20.0));

        when(discountRepository.findById(id)).thenReturn(Optional.empty());

        UpdateException exception = assertThrows(UpdateException.class, () -> discountService.updateDiscount(id, discountDto));
        assertTrue(exception.getMessage().contains(Discount.class.getSimpleName()));

        verify(discountRepository, times(1)).findById(id);
        verify(discountRepository, never()).save(any());
    }

    @Test
    void testDeleteDiscount_Success() {
        Long id = 1L;
        when(discountRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = discountService.deleteDiscount(id);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        verify(discountRepository, times(1)).existsById(id);
        verify(discountRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteDiscount_NotFound() {
        Long id = 1L;
        when(discountRepository.existsById(id)).thenReturn(false);

        DeleteException exception = assertThrows(DeleteException.class, () -> discountService.deleteDiscount(id));
        assertTrue(exception.getMessage().contains(Discount.class.getSimpleName()));

        verify(discountRepository, times(1)).existsById(id);
        verify(discountRepository, never()).deleteById(any());
    }
}
