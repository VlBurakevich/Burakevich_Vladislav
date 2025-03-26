package com.example.controller;

import com.example.dto.vehicle.TransportTypeDto;
import com.example.dto.vehicle.TransportTypeListDto;
import com.example.service.TransportTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
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
        TransportTypeDto transportTypeDto = new TransportTypeDto(1L, "Электромобиль", new BigDecimal("50.00"));
        TransportTypeListDto transportTypeListDto = new TransportTypeListDto(Collections.singletonList(transportTypeDto));

        when(transportTypeService.getTransportTypes(0, 10)).thenReturn(transportTypeListDto);

        TransportTypeListDto response = transportTypeController.getDiscounts(0, 10);

        assertEquals(transportTypeListDto, response);
        verify(transportTypeService, times(1)).getTransportTypes(0, 10);
    }

    @Test
    void testCreateTransportType() {
        TransportTypeDto transportTypeDto = new TransportTypeDto(null, "Электромобиль", new BigDecimal("50.00"));

        when(transportTypeService.createTransportType(transportTypeDto)).thenReturn(transportTypeDto);

        TransportTypeDto response = transportTypeController.createDiscount(transportTypeDto);

        assertEquals(transportTypeDto, response);
        verify(transportTypeService, times(1)).createTransportType(transportTypeDto);
    }

    @Test
    void testUpdateTransportType() {
        Long transportTypeId = 1L;
        TransportTypeDto transportTypeDto = new TransportTypeDto(transportTypeId, "Обновленный тип", new BigDecimal("60.00"));

        when(transportTypeService.updateTransportType(transportTypeId, transportTypeDto)).thenReturn(transportTypeDto);

        TransportTypeDto response = transportTypeController.updateDiscount(transportTypeId, transportTypeDto);

        assertEquals(transportTypeDto, response);
        verify(transportTypeService, times(1)).updateTransportType(transportTypeId, transportTypeDto);
    }

    @Test
    void testDeleteTransportType() {
        Long transportTypeId = 1L;

        doNothing().when(transportTypeService).deleteTransportType(transportTypeId);

        assertDoesNotThrow(() -> transportTypeController.deleteDiscount(transportTypeId));
        verify(transportTypeService, times(1)).deleteTransportType(transportTypeId);
    }
}
