package com.example.controller;

import com.example.dto.vehicle.VehicleDto;
import com.example.dto.vehicle.VehicleInfoDto;
import com.example.dto.vehicle.VehicleListDto;
import com.example.enums.VehiclesStatusEnum;
import com.example.service.VehicleService;
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
class VehicleControllerTest {

    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleService;

    @Test
    void testGetVehicles() {
        VehicleDto vehicleDto = new VehicleDto(1L, 2L, "SN123456", 80, VehiclesStatusEnum.AVAILABLE, 1000, 3L);
        VehicleListDto vehicleListDto = new VehicleListDto(Collections.singletonList(vehicleDto));

        when(vehicleService.getVehicles(0, 10)).thenReturn(vehicleListDto);

        VehicleListDto response = vehicleController.getVehicles(0, 10);

        assertEquals(vehicleListDto, response);
        verify(vehicleService, times(1)).getVehicles(0, 10);
    }

    @Test
    void testGetVehiclesByRentalPointId() {
        Long rentalPointId = 1L;
        VehicleDto vehicleDto = new VehicleDto(1L, 2L, "SN123456", 80, VehiclesStatusEnum.AVAILABLE, 1000, rentalPointId);
        VehicleListDto vehicleListDto = new VehicleListDto(Collections.singletonList(vehicleDto));

        when(vehicleService.getVehiclesByRentalPointId(rentalPointId, 0, 10)).thenReturn(vehicleListDto);

        VehicleListDto response = vehicleController.getVehiclesByRentalPointId(rentalPointId, 0, 10);

        assertEquals(vehicleListDto, response);
        verify(vehicleService, times(1)).getVehiclesByRentalPointId(rentalPointId, 0, 10);
    }

    @Test
    void testGetVehicleDetails() {
        Long vehicleId = 1L;
        VehicleInfoDto vehicleInfoDto = new VehicleInfoDto(vehicleId, "SN123456", "Tesla Model S", 80, 1000, Collections.emptyList());

        when(vehicleService.getVehicleDetails(vehicleId, 0, 10)).thenReturn(vehicleInfoDto);

        VehicleInfoDto response = vehicleController.getVehicleDetails(vehicleId, 0, 10);

        assertEquals(vehicleInfoDto, response);
        verify(vehicleService, times(1)).getVehicleDetails(vehicleId, 0, 10);
    }

    @Test
    void testCreateVehicle() {
        VehicleDto vehicleDto = new VehicleDto(null, 2L, "SN123456", 80, VehiclesStatusEnum.AVAILABLE, 1000, 3L);

        when(vehicleService.createVehicle(vehicleDto)).thenReturn(vehicleDto);

        VehicleDto response = vehicleController.createVehicle(vehicleDto);

        assertEquals(vehicleDto, response);
        verify(vehicleService, times(1)).createVehicle(vehicleDto);
    }

    @Test
    void testUpdateVehicle() {
        Long vehicleId = 1L;
        VehicleDto vehicleDto = new VehicleDto(vehicleId, 2L, "SN123456", 80, VehiclesStatusEnum.AVAILABLE, 1000, 3L);

        when(vehicleService.updateVehicle(vehicleId, vehicleDto)).thenReturn(vehicleDto);

        VehicleDto response = vehicleController.updateVehicle(vehicleId, vehicleDto);

        assertEquals(vehicleDto, response);
        verify(vehicleService, times(1)).updateVehicle(vehicleId, vehicleDto);
    }

    @Test
    void testDeleteVehicle() {
        Long vehicleId = 1L;
        vehicleController.deleteVehicle(vehicleId);
        verify(vehicleService, times(1)).deleteVehicle(vehicleId);
    }
}
