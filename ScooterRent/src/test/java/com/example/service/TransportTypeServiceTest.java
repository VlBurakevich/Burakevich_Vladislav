package com.example.service;

import com.example.dto.vehicle.TransportTypeDto;
import com.example.entity.TransportType;
import com.example.exceptions.CreateException;
import com.example.exceptions.UpdateException;
import com.example.mapper.TransportTypeMapper;
import com.example.repository.TransportTypeRepository;
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
class TransportTypeServiceTest {

    @InjectMocks
    private TransportTypeService transportTypeService;

    @Mock
    private TransportTypeRepository transportTypeRepository;

    @Mock
    private TransportTypeMapper transportTypeMapper;

    @Test
    void getAllTransportTypes() {
        TransportType transportType = new TransportType();
        transportType.setId(1L);
        transportType.setTypeName("Test Type");
        transportType.setBasePrice(BigDecimal.valueOf(100.0));

        TransportTypeDto transportTypeDto = new TransportTypeDto();
        transportTypeDto.setId(1L);
        transportTypeDto.setTypeName("Test Type");
        transportTypeDto.setBasePrice(BigDecimal.valueOf(100.0));

        Page<TransportType> transportTypePage = new PageImpl<>(Collections.singletonList(transportType));
        when(transportTypeRepository.findAll(any(PageRequest.class))).thenReturn(transportTypePage);
        when(transportTypeMapper.entityToDto(transportType)).thenReturn(transportTypeDto);

        ResponseEntity<List<TransportTypeDto>> response = transportTypeService.getTransportTypes(0, 10);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(transportTypeDto, response.getBody().getFirst());

        verify(transportTypeRepository, times(1)).findAll(any(PageRequest.class));
        verify(transportTypeMapper, times(1)).entityToDto(transportType);
    }

    @Test
    void createTransportType_Success() {
        TransportTypeDto transportTypeDto = new TransportTypeDto();
        transportTypeDto.setTypeName("Test Type");
        transportTypeDto.setBasePrice(BigDecimal.valueOf(100.0));

        TransportType transportType = new TransportType();
        transportType.setTypeName("Test Type");
        transportType.setBasePrice(BigDecimal.valueOf(100.0));

        when(transportTypeRepository.existsByTypeName(transportTypeDto.getTypeName())).thenReturn(false);
        when(transportTypeMapper.dtoToEntity(transportTypeDto)).thenReturn(transportType);
        when(transportTypeRepository.save(transportType)).thenReturn(transportType);
        when(transportTypeMapper.entityToDto(transportType)).thenReturn(transportTypeDto);

        ResponseEntity<TransportTypeDto> response = transportTypeService.createTransportType(transportTypeDto);

        assertNotNull(response.getBody());
        assertEquals(transportTypeDto, response.getBody());

        verify(transportTypeRepository, times(1)).existsByTypeName(transportTypeDto.getTypeName());
        verify(transportTypeRepository, times(1)).save(transportType);
        verify(transportTypeMapper, times(1)).dtoToEntity(transportTypeDto);
        verify(transportTypeMapper, times(1)).entityToDto(transportType);
    }

    @Test
    void createTransportType_AlreadyExist() {
        TransportTypeDto transportTypeDto = new TransportTypeDto();
        transportTypeDto.setTypeName("Test Type");

        when(transportTypeRepository.existsByTypeName(transportTypeDto.getTypeName())).thenReturn(true);

        CreateException exception = assertThrows(CreateException.class, () -> transportTypeService.createTransportType(transportTypeDto));
        assertTrue(exception.getMessage().contains(TransportType.class.getSimpleName()));

        verify(transportTypeRepository, times(1)).existsByTypeName(transportTypeDto.getTypeName());
        verify(transportTypeRepository, never()).save(any());
    }

    @Test
    void updateTransportType_Success() {
        Long id = 1L;
        TransportTypeDto transportTypeDto = new TransportTypeDto();
        transportTypeDto.setId(id);
        transportTypeDto.setTypeName("Updated Type");
        transportTypeDto.setBasePrice(BigDecimal.valueOf(150.0));

        TransportType existingType = new TransportType();
        existingType.setId(id);
        existingType.setTypeName("Old Type");
        existingType.setBasePrice(BigDecimal.valueOf(100.0));

        when(transportTypeRepository.findById(id)).thenReturn(Optional.of(existingType));
        when(transportTypeRepository.save(existingType)).thenReturn(existingType);
        when(transportTypeMapper.entityToDto(existingType)).thenReturn(transportTypeDto);

        ResponseEntity<TransportTypeDto> response = transportTypeService.updateTransportType(id, transportTypeDto);

        assertNotNull(response.getBody());
        assertEquals(transportTypeDto, response.getBody());

        verify(transportTypeRepository, times(1)).findById(id);
        verify(transportTypeRepository, times(1)).save(existingType);
        verify(transportTypeMapper, times(1)).entityToDto(existingType);
    }

    @Test
    void updateTransportType_NotFound() {
        Long id = 1L;
        TransportTypeDto transportTypeDto = new TransportTypeDto();
        transportTypeDto.setId(id);
        transportTypeDto.setTypeName("Updated Type");

        when(transportTypeRepository.findById(id)).thenReturn(Optional.empty());

        UpdateException exception = assertThrows(UpdateException.class, () -> transportTypeService.updateTransportType(id, transportTypeDto));
        assertTrue(exception.getMessage().contains(TransportType.class.getSimpleName()));

        verify(transportTypeRepository, times(1)).findById(id);
        verify(transportTypeRepository, never()).save(any());
    }

    @Test
    void testDeleteTransportType_Success() {
        Long id = 1L;
        when(transportTypeRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = transportTypeService.deleteTransportType(id);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        verify(transportTypeRepository, times(1)).existsById(id);
        verify(transportTypeRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTransportType_NotFound() {
        Long id = 1L;
        when(transportTypeRepository.existsById(id)).thenReturn(false);

        UpdateException exception = assertThrows(UpdateException.class, () -> transportTypeService.deleteTransportType(id));
        assertTrue(exception.getMessage().contains(TransportType.class.getSimpleName()));

        verify(transportTypeRepository, times(1)).existsById(id);
        verify(transportTypeRepository, never()).deleteById(any());
    }

}
