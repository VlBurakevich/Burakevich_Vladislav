package com.example.controller;

import com.example.dto.vehicle.VehicleDto;
import com.example.dto.vehicle.VehicleInfoDto;
import com.example.enums.VehiclesStatusEnum;
import com.example.service.VehicleService;
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
class VehicleControllerTest {

    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleService;

    @Test
    void testGetVehicles() {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(1L);
        vehicleDto.setModelId(2L);
        vehicleDto.setSerialNumber("SN123456");
        vehicleDto.setBatteryLevel(80);
        vehicleDto.setStatus(VehiclesStatusEnum.AVAILABLE);
        vehicleDto.setMileage(1000);
        vehicleDto.setRentalPointId(3L);

        List<VehicleDto> vehicleList = Collections.singletonList(vehicleDto);

        when(vehicleService.getVehicles(0, 10)).thenReturn(ResponseEntity.ok(vehicleList));

        ResponseEntity<List<VehicleDto>> response = vehicleController.getVehicles(0, 10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(vehicleList, response.getBody());
        verify(vehicleService, times(1)).getVehicles(0, 10);
    }

    @Test
    void testGetVehiclesByRentalPointId() {
        Long rentalPointId = 1L;
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(1L);
        vehicleDto.setModelId(2L);
        vehicleDto.setSerialNumber("SN123456");
        vehicleDto.setBatteryLevel(80);
        vehicleDto.setStatus(VehiclesStatusEnum.AVAILABLE);
        vehicleDto.setMileage(1000);
        vehicleDto.setRentalPointId(rentalPointId);

        List<VehicleDto> vehicleList = Collections.singletonList(vehicleDto);

        when(vehicleService.getVehiclesByRentalPointId(rentalPointId, 0, 10)).thenReturn(ResponseEntity.ok(vehicleList));

        ResponseEntity<List<VehicleDto>> response = vehicleController.getVehiclesByRentalPointId(rentalPointId, 0, 10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(vehicleList, response.getBody());
        verify(vehicleService, times(1)).getVehiclesByRentalPointId(rentalPointId, 0, 10);
    }

    @Test
    void testGetVehicleDetails() {
        Long vehicleId = 1L;
        VehicleInfoDto vehicleInfoDto = new VehicleInfoDto();
        vehicleInfoDto.setId(vehicleId);
        vehicleInfoDto.setSerialNumber("SN123456");
        vehicleInfoDto.setModelName("Tesla Model S");
        vehicleInfoDto.setBatteryLevel(80);
        vehicleInfoDto.setMileage(1000);

        when(vehicleService.getVehicleDetails(vehicleId, 0, 10)).thenReturn(ResponseEntity.ok(vehicleInfoDto));

        ResponseEntity<VehicleInfoDto> response = vehicleController.getVehicleDetails(vehicleId, 0, 10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(vehicleInfoDto, response.getBody());
        verify(vehicleService, times(1)).getVehicleDetails(vehicleId, 0, 10);
    }

    @Test
    void testCreateVehicle() {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setModelId(2L);
        vehicleDto.setSerialNumber("SN123456");
        vehicleDto.setBatteryLevel(80);
        vehicleDto.setStatus(VehiclesStatusEnum.AVAILABLE);
        vehicleDto.setMileage(1000);
        vehicleDto.setRentalPointId(3L);

        when(vehicleService.createVehicle(vehicleDto)).thenReturn(ResponseEntity.ok(vehicleDto));

        ResponseEntity<VehicleDto> response = vehicleController.createVehicle(vehicleDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(vehicleDto, response.getBody());
        verify(vehicleService, times(1)).createVehicle(vehicleDto);
    }

    @Test
    void testUpdateVehicle() {
        Long vehicleId = 1L;
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setModelId(2L);
        vehicleDto.setSerialNumber("SN123456");
        vehicleDto.setBatteryLevel(80);
        vehicleDto.setStatus(VehiclesStatusEnum.AVAILABLE);
        vehicleDto.setMileage(1000);
        vehicleDto.setRentalPointId(3L);

        when(vehicleService.updateVehicle(vehicleId, vehicleDto)).thenReturn(ResponseEntity.ok(vehicleDto));

        ResponseEntity<VehicleDto> response = vehicleController.updateVehicle(vehicleId, vehicleDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(vehicleDto, response.getBody());
        verify(vehicleService, times(1)).updateVehicle(vehicleId, vehicleDto);
    }

    @Test
    void testDeleteVehicle() {
        Long vehicleId = 1L;
        when(vehicleService.deleteVehicle(vehicleId)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = vehicleController.deleteVehicle(vehicleId);

        assertEquals(204, response.getStatusCode().value());
        verify(vehicleService, times(1)).deleteVehicle(vehicleId);
    }
}
