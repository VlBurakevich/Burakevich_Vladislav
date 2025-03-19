package com.example.controller;

import com.example.dto.finance.TarifDto;
import com.example.enums.TarifTypeEnum;
import com.example.service.TarifService;
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
class TarifControllerTest {

    @InjectMocks
    private TarifController tarifController;

    @Mock
    private TarifService tarifService;

    @Test
    void testGetTarifs() {
        TarifDto tarifDto = new TarifDto();
        tarifDto.setId(1L);
        tarifDto.setName("Стандартный");
        tarifDto.setType(TarifTypeEnum.HOURLY);
        tarifDto.setBasePrice(new BigDecimal("100.00"));
        tarifDto.setUnitTime(60);
        tarifDto.setTransportTypeId(1L);

        List<TarifDto> tarifList = Collections.singletonList(tarifDto);

        when(tarifService.getTarifs(0, 10)).thenReturn(ResponseEntity.ok(tarifList));

        ResponseEntity<List<TarifDto>> response = tarifController.getTarifs(0, 10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(tarifList, response.getBody());
        verify(tarifService, times(1)).getTarifs(0, 10);
    }

    @Test
    void testCreateTarif() {
        TarifDto tarifDto = new TarifDto();
        tarifDto.setName("Премиум");
        tarifDto.setType(TarifTypeEnum.SUBSCRIPTION);
        tarifDto.setBasePrice(new BigDecimal("200.00"));
        tarifDto.setUnitTime(30);
        tarifDto.setTransportTypeId(2L);

        when(tarifService.createTarif(tarifDto)).thenReturn(ResponseEntity.ok(tarifDto));

        ResponseEntity<TarifDto> response = tarifController.createTarif(tarifDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(tarifDto, response.getBody());
        verify(tarifService, times(1)).createTarif(tarifDto);
    }

    @Test
    void testUpdateTarif() {
        Long tarifId = 1L;
        TarifDto tarifDto = new TarifDto();
        tarifDto.setName("Обновленный тариф");
        tarifDto.setType(TarifTypeEnum.HOURLY);
        tarifDto.setBasePrice(new BigDecimal("150.00"));
        tarifDto.setUnitTime(45);
        tarifDto.setTransportTypeId(3L);

        when(tarifService.updateTarif(tarifId, tarifDto)).thenReturn(ResponseEntity.ok(tarifDto));

        ResponseEntity<TarifDto> response = tarifController.updateTarif(tarifId, tarifDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(tarifDto, response.getBody());
        verify(tarifService, times(1)).updateTarif(tarifId, tarifDto);
    }

    @Test
    void testDeleteTarif() {
        Long tarifId = 1L;
        when(tarifService.deleteTarif(tarifId)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = tarifController.deleteTarif(tarifId);

        assertEquals(204, response.getStatusCode().value());
        verify(tarifService, times(1)).deleteTarif(tarifId);
    }
}
