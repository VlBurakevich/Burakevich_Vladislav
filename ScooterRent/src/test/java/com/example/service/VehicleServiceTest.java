package com.example.service;

import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.vehicle.VehicleDto;
import com.example.dto.vehicle.VehicleInfoDto;
import com.example.dto.vehicle.VehicleListDto;
import com.example.entity.Model;
import com.example.entity.RentalPoint;
import com.example.entity.Vehicle;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(1L);
        vehicleDto.setSerialNumber("SN123456");

        Page<Vehicle> vehiclePage = new PageImpl<>(Collections.singletonList(vehicle));
        when(vehicleRepository.findAll(any(PageRequest.class))).thenReturn(vehiclePage);
        when(vehicleMapper.entityToDto(vehicle)).thenReturn(vehicleDto);

        VehicleListDto result = vehicleService.getVehicles(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getVehicles().size());
        assertEquals(vehicleDto, result.getVehicles().getFirst());

        verify(vehicleRepository, times(1)).findAll(any(PageRequest.class));
        verify(vehicleMapper, times(1)).entityToDto(vehicle);
    }

    @Test
    void testGetVehiclesByRentalPointId() {
        Long rentalPointId = 1L;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setSerialNumber("SN123456");

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(1L);
        vehicleDto.setSerialNumber("SN123456");

        Page<Vehicle> vehiclePage = new PageImpl<>(Collections.singletonList(vehicle));
        when(vehicleRepository.findAllByRentalPointId(rentalPointId, PageRequest.of(0, 10))).thenReturn(vehiclePage);
        when(vehicleMapper.entityToDto(vehicle)).thenReturn(vehicleDto);

        VehicleListDto result = vehicleService.getVehiclesByRentalPointId(rentalPointId, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getVehicles().size());
        assertEquals(vehicleDto, result.getVehicles().getFirst());

        verify(vehicleRepository, times(1)).findAllByRentalPointId(rentalPointId, PageRequest.of(0, 10));
        verify(vehicleMapper, times(1)).entityToDto(vehicle);
    }

    @Test
    void testGetVehicleDetails() {
        Long vehicleId = 1L;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        vehicle.setSerialNumber("SN123456");

        List<RentalShortInfoDto> rentals = Collections.emptyList();
        VehicleInfoDto vehicleInfoDto = new VehicleInfoDto();
        vehicleInfoDto.setId(vehicleId);
        vehicleInfoDto.setSerialNumber("SN123456");

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(rentalService.getAllRentalsByVehicleId(0, 10, vehicleId)).thenReturn(rentals);
        when(vehicleInfoMapper.toVehicleInfoDto(vehicle, rentals)).thenReturn(vehicleInfoDto);

        VehicleInfoDto result = vehicleService.getVehicleDetails(vehicleId, 0, 10);

        assertNotNull(result);
        assertEquals(vehicleInfoDto, result);

        verify(vehicleRepository, times(1)).findById(vehicleId);
        verify(rentalService, times(1)).getAllRentalsByVehicleId(0, 10, vehicleId);
        verify(vehicleInfoMapper, times(1)).toVehicleInfoDto(vehicle, rentals);
    }

    @Test
    void testCreateVehicle_Success() {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setSerialNumber("SN123456");
        vehicleDto.setRentalPointId(1L);

        Vehicle vehicle = new Vehicle();
        vehicle.setSerialNumber("SN123456");

        RentalPoint rentalPoint = new RentalPoint();
        rentalPoint.setId(1L);

        when(vehicleRepository.existsBySerialNumber(vehicleDto.getSerialNumber())).thenReturn(false);
        when(vehicleMapper.dtoToEntity(vehicleDto)).thenReturn(vehicle);
        when(rentalPointRepository.findById(vehicleDto.getRentalPointId())).thenReturn(Optional.of(rentalPoint));
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        when(vehicleMapper.entityToDto(vehicle)).thenReturn(vehicleDto);

        VehicleDto result = vehicleService.createVehicle(vehicleDto);

        assertNotNull(result);
        assertEquals(vehicleDto, result);
        assertEquals(rentalPoint, vehicle.getRentalPoint());

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

        VehicleDto result = vehicleService.updateVehicle(id, vehicleDto);

        assertNotNull(result);
        assertEquals(vehicleDto, result);
        verify(vehicleMapper, times(1)).updateEntityFromDto(vehicleDto, existingVehicle);

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

        assertDoesNotThrow(() -> vehicleService.deleteVehicle(id));

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
}
