package com.example.service;

import com.example.dto.finance.TarifDto;
import com.example.entity.Tarif;
import com.example.entity.TransportType;
import com.example.exceptions.CreateException;
import com.example.exceptions.DeleteException;
import com.example.exceptions.GetException;
import com.example.exceptions.UpdateException;
import com.example.mapper.TarifMapper;
import com.example.repository.TarifRepository;
import com.example.repository.TransportTypeRepository;
import com.example.enums.TarifTypeEnum;
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
class TarifServiceTest {

    @InjectMocks
    private TarifService tarifService;

    @Mock
    private TarifRepository tarifRepository;

    @Mock
    private TransportTypeRepository transportTypeRepository;

    @Mock
    private TarifMapper tarifMapper;

    @Test
    void testGetTarifs() {
        Tarif tarif = new Tarif();
        tarif.setId(1L);
        tarif.setName("Test Tarif");
        tarif.setType(TarifTypeEnum.HOURLY);
        tarif.setBasePrice(BigDecimal.valueOf(100.0));
        tarif.setUnitTime(60);
        TransportType transportType = new TransportType();
        transportType.setId(1L);
        tarif.setTransportType(transportType);

        TarifDto tarifDto = new TarifDto();
        tarifDto.setId(1L);
        tarifDto.setName("Test Tarif");
        tarifDto.setType(TarifTypeEnum.HOURLY);
        tarifDto.setBasePrice(BigDecimal.valueOf(100.0));
        tarifDto.setUnitTime(60);
        tarifDto.setTransportTypeId(1L);

        Page<Tarif> tarifPage = new PageImpl<>(Collections.singletonList(tarif));
        when(tarifRepository.findAll(any(PageRequest.class))).thenReturn(tarifPage);
        when(tarifMapper.entityToDto(tarif)).thenReturn(tarifDto);

        ResponseEntity<List<TarifDto>> response = tarifService.getTarifs(0, 10);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(tarifDto, response.getBody().getFirst());

        verify(tarifRepository, times(1)).findAll(any(PageRequest.class));
        verify(tarifMapper, times(1)).entityToDto(tarif);
    }

    @Test
    void testCreateTarif_Success() {
        TarifDto tarifDto = new TarifDto();
        tarifDto.setName("Test Tarif");
        tarifDto.setType(TarifTypeEnum.SUBSCRIPTION);
        tarifDto.setBasePrice(BigDecimal.valueOf(100.0));
        tarifDto.setUnitTime(60);
        tarifDto.setTransportTypeId(1L);

        Tarif tarif = new Tarif();
        tarif.setName("Test Tarif");
        tarif.setType(TarifTypeEnum.SUBSCRIPTION);
        tarif.setBasePrice(BigDecimal.valueOf(100.0));
        tarif.setUnitTime(60);

        TransportType transportType = new TransportType();
        transportType.setId(1L);

        when(tarifRepository.existsByName(tarifDto.getName())).thenReturn(false);
        when(tarifMapper.dtoToEntity(tarifDto)).thenReturn(tarif);
        when(transportTypeRepository.findById(tarifDto.getTransportTypeId())).thenReturn(Optional.of(transportType));
        when(tarifRepository.save(tarif)).thenReturn(tarif);
        when(tarifMapper.entityToDto(tarif)).thenReturn(tarifDto);


        ResponseEntity<TarifDto> response = tarifService.createTarif(tarifDto);


        assertNotNull(response.getBody());
        assertEquals(tarifDto, response.getBody());

        verify(tarifRepository, times(1)).existsByName(tarifDto.getName());
        verify(transportTypeRepository, times(1)).findById(tarifDto.getTransportTypeId());
        verify(tarifRepository, times(1)).save(tarif);
        verify(tarifMapper, times(1)).dtoToEntity(tarifDto);
        verify(tarifMapper, times(1)).entityToDto(tarif);
    }

    @Test
    void testCreateTarif_AlreadyExists() {
        TarifDto tarifDto = new TarifDto();
        tarifDto.setName("Test Tarif");

        when(tarifRepository.existsByName(tarifDto.getName())).thenReturn(true);

        // Act & Assert
        CreateException exception = assertThrows(CreateException.class, () -> tarifService.createTarif(tarifDto));
        assertTrue(exception.getMessage().contains(Tarif.class.getSimpleName()));

        verify(tarifRepository, times(1)).existsByName(tarifDto.getName());
        verify(tarifRepository, never()).save(any());
    }

    @Test
    void testCreateTarif_TransportTypeNotFound() {
        TarifDto tarifDto = new TarifDto();
        tarifDto.setName("Test Tarif");
        tarifDto.setTransportTypeId(1L);

        when(tarifRepository.existsByName(tarifDto.getName())).thenReturn(false);
        when(transportTypeRepository.findById(tarifDto.getTransportTypeId())).thenReturn(Optional.empty());

        GetException exception = assertThrows(GetException.class, () -> tarifService.createTarif(tarifDto));
        assertTrue(exception.getMessage().contains(TransportType.class.getSimpleName()));

        verify(tarifRepository, times(1)).existsByName(tarifDto.getName());
        verify(transportTypeRepository, times(1)).findById(tarifDto.getTransportTypeId());
        verify(tarifRepository, never()).save(any());
    }

    @Test
    void testUpdateTarif_Success() {
        Long id = 1L;
        TarifDto tarifDto = new TarifDto();
        tarifDto.setId(id);
        tarifDto.setName("Updated Tarif");
        tarifDto.setType(TarifTypeEnum.HOURLY);
        tarifDto.setBasePrice(BigDecimal.valueOf(150.0));
        tarifDto.setUnitTime(90);
        tarifDto.setTransportTypeId(1L);

        Tarif existingTarif = new Tarif();
        existingTarif.setId(id);
        existingTarif.setName("Old Tarif");
        existingTarif.setType(TarifTypeEnum.SUBSCRIPTION);
        existingTarif.setBasePrice(BigDecimal.valueOf(100.0));
        existingTarif.setUnitTime(60);
        TransportType transportType = new TransportType();
        transportType.setId(1L);
        existingTarif.setTransportType(transportType);

        when(tarifRepository.findById(id)).thenReturn(Optional.of(existingTarif));
        when(tarifRepository.save(existingTarif)).thenReturn(existingTarif);
        when(tarifMapper.entityToDto(existingTarif)).thenReturn(tarifDto);

        ResponseEntity<TarifDto> response = tarifService.updateTarif(id, tarifDto);

        assertNotNull(response.getBody());
        assertEquals(tarifDto, response.getBody());

        verify(tarifRepository, times(1)).findById(id);
        verify(tarifRepository, times(1)).save(existingTarif);
        verify(tarifMapper, times(1)).entityToDto(existingTarif);
    }

    @Test
    void testUpdateTarif_NotFound() {
        Long id = 1L;
        TarifDto tarifDto = new TarifDto();
        tarifDto.setId(id);
        tarifDto.setName("Updated Tarif");

        when(tarifRepository.findById(id)).thenReturn(Optional.empty());

        UpdateException exception = assertThrows(UpdateException.class, () -> tarifService.updateTarif(id, tarifDto));
        assertTrue(exception.getMessage().contains(Tarif.class.getSimpleName()));

        verify(tarifRepository, times(1)).findById(id);
        verify(tarifRepository, never()).save(any());
    }

    @Test
    void testDeleteTarif_Success() {
        Long id = 1L;
        when(tarifRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = tarifService.deleteTarif(id);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        verify(tarifRepository, times(1)).existsById(id);
        verify(tarifRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTarif_NotFound() {
        Long id = 1L;
        when(tarifRepository.existsById(id)).thenReturn(false);

        DeleteException exception = assertThrows(DeleteException.class, () -> tarifService.deleteTarif(id));
        assertTrue(exception.getMessage().contains(Tarif.class.getSimpleName()));

        verify(tarifRepository, times(1)).existsById(id);
        verify(tarifRepository, never()).deleteById(any());
    }
}
