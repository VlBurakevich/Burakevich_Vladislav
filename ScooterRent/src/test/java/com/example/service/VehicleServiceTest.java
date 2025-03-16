package com.example.service;

import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.vehicle.VehicleDto;
import com.example.dto.vehicle.VehicleInfoDto;
import com.example.entity.Model;
import com.example.entity.RentalPoint;
import com.example.entity.Vehicle;
import com.example.enums.VehiclesStatusEnum;
import com.example.exceptions.CreateException;
import com.example.exceptions.DeleteException;
import com.example.exceptions.GetException;
import com.example.mapper.VehicleInfoMapper;
import com.example.mapper.VehicleMapper;
import com.example.repository.ModelRepository;
import com.example.repository.RentalPointRepository;
import com.example.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private RentalPointRepository rentalPointRepository;

    @Mock
    private RentalService rentalService;

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @Mock
    private VehicleInfoMapper vehicleInfoMapper;

    @Test
    void testGetVehicles() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setSerialNumber("SN123456");
        vehicle.setBatteryLevel(80);
        vehicle.setStatus(VehiclesStatusEnum.AVAILABLE);
        vehicle.setMileage(1000);

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(1L);
        vehicleDto.setSerialNumber("SN123456");
        vehicleDto.setBatteryLevel(80);
        vehicleDto.setStatus(VehiclesStatusEnum.AVAILABLE);
        vehicleDto.setMileage(1000);

        Page<Vehicle> vehiclePage = new PageImpl<>(Collections.singletonList(vehicle));
        when(vehicleRepository.findAll(any(PageRequest.class))).thenReturn(vehiclePage);
        when(vehicleMapper.entityToDto(vehicle)).thenReturn(vehicleDto);

        ResponseEntity<List<VehicleDto>> response = vehicleService.getVehicles(0, 10);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(vehicleDto, response.getBody().getFirst());

        verify(vehicleRepository, times(1)).findAll(any(PageRequest.class));
        verify(vehicleMapper, times(1)).entityToDto(vehicle);
    }

    @Test
    void testGetVehiclesByRentalPointId() {
        Long rentalPointId = 1L;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setSerialNumber("SN123456");
        vehicle.setBatteryLevel(80);
        vehicle.setStatus(VehiclesStatusEnum.AVAILABLE);
        vehicle.setMileage(1000);
        vehicle.setRentalPoint(new RentalPoint());

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(1L);
        vehicleDto.setSerialNumber("SN123456");
        vehicleDto.setBatteryLevel(80);
        vehicleDto.setStatus(VehiclesStatusEnum.AVAILABLE);
        vehicleDto.setMileage(1000);

        Page<Vehicle> vehiclePage = new PageImpl<>(Collections.singletonList(vehicle));
        when(vehicleRepository.findAllByRentalPointId(rentalPointId, PageRequest.of(0, 10))).thenReturn(vehiclePage);
        when(vehicleMapper.entityToDto(vehicle)).thenReturn(vehicleDto);

        ResponseEntity<List<VehicleDto>> response = vehicleService.getVehiclesByRentalPointId(rentalPointId, 0, 10);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(vehicleDto, response.getBody().getFirst());

        verify(vehicleRepository, times(1)).findAllByRentalPointId(rentalPointId, PageRequest.of(0, 10));
        verify(vehicleMapper, times(1)).entityToDto(vehicle);
    }

    @Test
    void testCreateVehicle_Success() {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setSerialNumber("SN123456");
        vehicleDto.setBatteryLevel(80);
        vehicleDto.setStatus(VehiclesStatusEnum.AVAILABLE);
        vehicleDto.setMileage(1000);
        vehicleDto.setRentalPointId(1L);

        Vehicle vehicle = new Vehicle();
        vehicle.setSerialNumber("SN123456");
        vehicle.setBatteryLevel(80);
        vehicle.setStatus(VehiclesStatusEnum.AVAILABLE);
        vehicle.setMileage(1000);

        RentalPoint rentalPoint = new RentalPoint();
        rentalPoint.setId(1L);

        when(vehicleRepository.existsBySerialNumber(vehicleDto.getSerialNumber())).thenReturn(false);
        when(vehicleMapper.dtoToEntity(vehicleDto)).thenReturn(vehicle);
        when(rentalPointRepository.findById(vehicleDto.getRentalPointId())).thenReturn(Optional.of(rentalPoint));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(vehicleMapper.entityToDto(vehicle)).thenReturn(vehicleDto);

        ResponseEntity<VehicleDto> response = vehicleService.createVehicle(vehicleDto);

        assertNotNull(response.getBody());
        assertEquals(vehicleDto, response.getBody());

        verify(vehicleRepository, times(1)).existsBySerialNumber(vehicleDto.getSerialNumber());
        verify(vehicleMapper, times(1)).dtoToEntity(vehicleDto);
        verify(rentalPointRepository, times(1)).findById(vehicleDto.getRentalPointId());
        verify(vehicleRepository, times(1)).save(vehicle);
        verify(vehicleMapper, times(1)).entityToDto(vehicle);
    }

    @Test
    void testCreateVehicle_AlreadyExists() {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setSerialNumber("SN123456");

        when(vehicleRepository.existsBySerialNumber(vehicleDto.getSerialNumber())).thenReturn(true);

        CreateException exception = assertThrows(CreateException.class, () -> vehicleService.createVehicle(vehicleDto));
        assertTrue(exception.getMessage().contains(Vehicle.class.getSimpleName()));

        verify(vehicleRepository, times(1)).existsBySerialNumber(vehicleDto.getSerialNumber());
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void testUpdateVehicle_Success() {
        Long id = 1L;
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(id);
        vehicleDto.setSerialNumber("SN123456");
        vehicleDto.setBatteryLevel(80);
        vehicleDto.setStatus(VehiclesStatusEnum.AVAILABLE);
        vehicleDto.setMileage(1000);
        vehicleDto.setRentalPointId(1L);
        vehicleDto.setModelId(2L);

        Vehicle existingVehicle = new Vehicle();
        existingVehicle.setId(id);
        existingVehicle.setSerialNumber("SN123456");

        RentalPoint rentalPoint = new RentalPoint();
        rentalPoint.setId(1L);

        Model model = new Model();
        model.setId(2L);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(existingVehicle));
        when(rentalPointRepository.findById(vehicleDto.getRentalPointId())).thenReturn(Optional.of(rentalPoint));
        when(modelRepository.findById(vehicleDto.getModelId())).thenReturn(Optional.of(model));
        when(vehicleRepository.save(existingVehicle)).thenReturn(existingVehicle);
        when(vehicleMapper.entityToDto(existingVehicle)).thenReturn(vehicleDto);

        ResponseEntity<VehicleDto> response = vehicleService.updateVehicle(id, vehicleDto);

        assertNotNull(response.getBody());
        assertEquals(vehicleDto, response.getBody());

        verify(vehicleRepository, times(1)).findById(id);
        verify(rentalPointRepository, times(1)).findById(vehicleDto.getRentalPointId());
        verify(modelRepository, times(1)).findById(vehicleDto.getModelId());
        verify(vehicleRepository, times(1)).save(existingVehicle);
        verify(vehicleMapper, times(1)).entityToDto(existingVehicle);
    }

    @Test
    void testUpdateVehicle_NotFound() {
        Long id = 1L;
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(id);

        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        GetException exception = assertThrows(GetException.class, () -> vehicleService.updateVehicle(id, vehicleDto));
        assertTrue(exception.getMessage().contains(Vehicle.class.getSimpleName()));

        verify(vehicleRepository, times(1)).findById(id);
        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void testDeleteVehicle_Success() {
        Long id = 1L;
        when(vehicleRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = vehicleService.deleteVehicle(id);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        verify(vehicleRepository, times(1)).existsById(id);
        verify(vehicleRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteVehicle_NotFound() {
        Long id = 1L;
        when(vehicleRepository.existsById(id)).thenReturn(false);

        DeleteException exception = assertThrows(DeleteException.class, () -> vehicleService.deleteVehicle(id));
        assertTrue(exception.getMessage().contains(Vehicle.class.getSimpleName()));

        verify(vehicleRepository, times(1)).existsById(id);
        verify(vehicleRepository, never()).deleteById(any());
    }

    @Test
    void testGetVehicleDetails_Success() {
        Long id = 1L;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        vehicle.setSerialNumber("SN123456");
        vehicle.setBatteryLevel(80);
        vehicle.setMileage(1000);

        List<RentalShortInfoDto> rentals = Collections.emptyList();

        VehicleInfoDto vehicleInfoDto = new VehicleInfoDto();
        vehicleInfoDto.setId(id);
        vehicleInfoDto.setSerialNumber("SN123456");
        vehicleInfoDto.setBatteryLevel(80);
        vehicleInfoDto.setMileage(1000);
        vehicleInfoDto.setRentals(rentals);

        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        when(rentalService.getAllRentalsByVehicleId(0, 10, id)).thenReturn(ResponseEntity.ok(rentals));
        when(vehicleInfoMapper.toVehicleInfoDto(vehicle, rentals)).thenReturn(vehicleInfoDto);

        ResponseEntity<VehicleInfoDto> response = vehicleService.getVehicleDetails(id, 0, 10);

        assertNotNull(response.getBody());
        assertEquals(vehicleInfoDto, response.getBody());

        verify(vehicleRepository, times(1)).findById(id);
        verify(rentalService, times(1)).getAllRentalsByVehicleId(0, 10, id);
        verify(vehicleInfoMapper, times(1)).toVehicleInfoDto(vehicle, rentals);
    }

    @Test
    void testGetVehicleDetails_NotFound() {
        Long id = 1L;
        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        GetException exception = assertThrows(GetException.class, () -> vehicleService.getVehicleDetails(id, 0, 10));
        assertTrue(exception.getMessage().contains(Vehicle.class.getSimpleName()));

        verify(vehicleRepository, times(1)).findById(id);
        verify(rentalService, never()).getAllRentalsByVehicleId(anyInt(), anyInt(), anyLong());
    }
}
