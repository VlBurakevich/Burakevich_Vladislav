package com.example.controller;

import com.example.dto.finance.TarifDto;
import com.example.dto.finance.TarifListDto;
import com.example.enums.TarifTypeEnum;
import com.example.service.TarifService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

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
        TarifDto tarifDto = new TarifDto(1L, "Стандартный", TarifTypeEnum.HOURLY, new BigDecimal("100.00"), 60, 1L);
        TarifListDto tarifListDto = new TarifListDto(Collections.singletonList(tarifDto));

        when(tarifService.getTarifs(0, 10)).thenReturn(tarifListDto);

        TarifListDto response = tarifController.getTarifs(0, 10);

        assertEquals(tarifListDto, response);
        verify(tarifService, times(1)).getTarifs(0, 10);
    }

    @Test
    void testCreateTarif() {
        TarifDto tarifDto = new TarifDto(null, "Премиум", TarifTypeEnum.SUBSCRIPTION, new BigDecimal("200.00"), 30, 2L);

        when(tarifService.createTarif(tarifDto)).thenReturn(tarifDto);

        TarifDto response = tarifController.createTarif(tarifDto);

        assertEquals(tarifDto, response);
        verify(tarifService, times(1)).createTarif(tarifDto);
    }

    @Test
    void testUpdateTarif() {
        Long tarifId = 1L;
        TarifDto tarifDto = new TarifDto(tarifId, "Обновленный тариф", TarifTypeEnum.HOURLY, new BigDecimal("150.00"), 45, 3L);

        when(tarifService.updateTarif(tarifId, tarifDto)).thenReturn(tarifDto);

        TarifDto response = tarifController.updateTarif(tarifId, tarifDto);

        assertEquals(tarifDto, response);
        verify(tarifService, times(1)).updateTarif(tarifId, tarifDto);
    }

    @Test
    void testDeleteTarif() {
        Long tarifId = 1L;
        tarifService.deleteTarif(tarifId);

        verify(tarifService, times(1)).deleteTarif(tarifId);
    }
}
