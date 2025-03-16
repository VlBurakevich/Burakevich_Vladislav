package com.example.controller;

import com.example.dto.vehicle.ModelDto;
import com.example.service.ModelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

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

        List<ModelDto> modelList = Collections.singletonList(modelDto);

        when(modelService.getModelVehicles(0, 10)).thenReturn(ResponseEntity.ok(modelList));

        ResponseEntity<List<ModelDto>> response = modelController.getVehicles(0, 10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(modelList, response.getBody());
        verify(modelService, times(1)).getModelVehicles(0, 10);
    }

    @Test
    void testCreateTarif() {
        ModelDto modelDto = new ModelDto();
        modelDto.setModelName("Tesla Model S");
        modelDto.setMaxSpeed(250);
        modelDto.setTransportTypeId(1L);

        when(modelService.createModelVehicle(modelDto)).thenReturn(ResponseEntity.ok(modelDto));

        ResponseEntity<ModelDto> response = modelController.createTarif(modelDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(modelDto, response.getBody());
        verify(modelService, times(1)).createModelVehicle(modelDto);
    }

    @Test
    void testUpdateTarif() {
        Long modelId = 1L;
        ModelDto modelDto = new ModelDto();
        modelDto.setModelName("Tesla Model X");
        modelDto.setMaxSpeed(250);
        modelDto.setTransportTypeId(1L);

        when(modelService.updateModelVehicle(modelId, modelDto)).thenReturn(ResponseEntity.ok(modelDto));

        ResponseEntity<ModelDto> response = modelController.updateTarif(modelId, modelDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(modelDto, response.getBody());
        verify(modelService, times(1)).updateModelVehicle(modelId, modelDto);
    }

    @Test
    void testDeleteTarif() {
        Long modelId = 1L;
        when(modelService.deleteModelVehicle(modelId)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = modelController.deleteTarif(modelId);

        assertEquals(204, response.getStatusCode().value());
        verify(modelService, times(1)).deleteModelVehicle(modelId);
    }
}
