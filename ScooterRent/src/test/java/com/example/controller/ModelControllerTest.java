package com.example.controller;

import com.example.dto.vehicle.ModelDto;
import com.example.dto.vehicle.ModelListDto;
import com.example.service.ModelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModelControllerTest {

    @InjectMocks
    private ModelController modelController;

    @Mock
    private ModelService modelService;

    @Test
    void testGetVehicles() {
        ModelDto modelDto = new ModelDto();
        modelDto.setId(1L);
        modelDto.setModelName("Tesla Model S");
        modelDto.setMaxSpeed(250);
        modelDto.setTransportTypeId(1L);

        ModelListDto modelListDto = new ModelListDto(Collections.singletonList(modelDto));

        when(modelService.getModelVehicles(0, 10)).thenReturn(modelListDto);

        ModelListDto response = modelController.getVehicles(0, 10);

        assertEquals(1, response.getModels().size());
        assertEquals(modelDto, response.getModels().getFirst());
        verify(modelService, times(1)).getModelVehicles(0, 10);
    }

    @Test
    void testCreateTarif() {
        ModelDto modelDto = new ModelDto();
        modelDto.setModelName("Tesla Model S");
        modelDto.setMaxSpeed(250);
        modelDto.setTransportTypeId(1L);

        when(modelService.createModelVehicle(modelDto)).thenReturn(modelDto);

        ModelDto response = modelController.createTarif(modelDto);

        assertEquals(modelDto, response);
        verify(modelService, times(1)).createModelVehicle(modelDto);
    }

    @Test
    void testUpdateTarif() {
        Long modelId = 1L;
        ModelDto modelDto = new ModelDto();
        modelDto.setModelName("Tesla Model X");
        modelDto.setMaxSpeed(250);
        modelDto.setTransportTypeId(1L);

        when(modelService.updateModelVehicle(modelId, modelDto)).thenReturn(modelDto);

        ModelDto response = modelController.updateTarif(modelId, modelDto);

        assertEquals(modelDto, response);
        verify(modelService, times(1)).updateModelVehicle(modelId, modelDto);
    }

    @Test
    void testDeleteTarif() {
        Long modelId = 1L;

        modelController.deleteTarif(modelId);

        verify(modelService, times(1)).deleteModelVehicle(modelId);
    }
}
