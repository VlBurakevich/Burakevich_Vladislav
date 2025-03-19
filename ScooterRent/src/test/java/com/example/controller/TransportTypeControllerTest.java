package com.example.controller;

import com.example.controller.TransportTypeController;
import com.example.dto.vehicle.TransportTypeDto;
import com.example.service.TransportTypeService;
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
class TransportTypeControllerTest {

    @InjectMocks
    private TransportTypeController transportTypeController;

    @Mock
    private TransportTypeService transportTypeService;

    @Test
    void testGetTransportTypes() {
        TransportTypeDto transportTypeDto = new TransportTypeDto();
        transportTypeDto.setId(1L);
        transportTypeDto.setTypeName("Электромобиль");
        transportTypeDto.setBasePrice(new BigDecimal("50.00"));

        List<TransportTypeDto> transportTypeList = Collections.singletonList(transportTypeDto);

        when(transportTypeService.getTransportTypes(0, 10)).thenReturn(ResponseEntity.ok(transportTypeList));

        ResponseEntity<List<TransportTypeDto>> response = transportTypeController.getDiscounts(0, 10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(transportTypeList, response.getBody());
        verify(transportTypeService, times(1)).getTransportTypes(0, 10);
    }

    @Test
    void testCreateTransportType() {
        TransportTypeDto transportTypeDto = new TransportTypeDto();
        transportTypeDto.setTypeName("Электромобиль");
        transportTypeDto.setBasePrice(new BigDecimal("50.00"));

        when(transportTypeService.createTransportType(transportTypeDto)).thenReturn(ResponseEntity.ok(transportTypeDto));

        ResponseEntity<TransportTypeDto> response = transportTypeController.createDiscount(transportTypeDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(transportTypeDto, response.getBody());
        verify(transportTypeService, times(1)).createTransportType(transportTypeDto);
    }

    @Test
    void testUpdateTransportType() {
        Long transportTypeId = 1L;
        TransportTypeDto transportTypeDto = new TransportTypeDto();
        transportTypeDto.setTypeName("Обновленный тип");
        transportTypeDto.setBasePrice(new BigDecimal("60.00"));

        when(transportTypeService.updateTransportType(transportTypeId, transportTypeDto))
                .thenReturn(ResponseEntity.ok(transportTypeDto));

        ResponseEntity<TransportTypeDto> response = transportTypeController.updateDiscount(transportTypeId, transportTypeDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(transportTypeDto, response.getBody());
        verify(transportTypeService, times(1)).updateTransportType(transportTypeId, transportTypeDto);
    }

    @Test
    void testDeleteTransportType() {
        Long transportTypeId = 1L;
        when(transportTypeService.deleteTransportType(transportTypeId)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = transportTypeController.deleteDiscount(transportTypeId);

        assertEquals(204, response.getStatusCode().value());
        verify(transportTypeService, times(1)).deleteTransportType(transportTypeId);
    }
}
