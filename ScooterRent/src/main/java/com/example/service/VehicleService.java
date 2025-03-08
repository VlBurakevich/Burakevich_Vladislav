package com.example.service;

import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.vehicle.VehicleDto;
import com.example.dto.vehicle.VehicleInfoDto;
import com.example.entity.RentalPoint;
import com.example.entity.Vehicle;
import com.example.exceptions.CreateException;
import com.example.exceptions.DeleteException;
import com.example.exceptions.GetException;
import com.example.mapper.VehicleInfoMapper;
import com.example.mapper.VehicleMapper;
import com.example.repository.RentalPointRepository;
import com.example.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final RentalPointRepository rentalPointRepository;
    private final RentalService rentalService;

    public ResponseEntity<List<VehicleDto>> getVehicles(int page, int size) {
        Page<Vehicle> vehiclePage = vehicleRepository.findAll(PageRequest.of(page, size));
        List<VehicleDto> vehicles = vehiclePage.getContent()
                .stream()
                .map(VehicleMapper.INSTANCE::entityToDto)
                .toList();

        return ResponseEntity.ok(vehicles);
    }

    public ResponseEntity<List<VehicleDto>> getVehiclesByRentalPointId(Long id, int page, int size) {
        Page<Vehicle> vehiclePage = vehicleRepository.findAllByRentalPointId(id, PageRequest.of(page, size));
        List<VehicleDto> vehicles = vehiclePage.getContent()
                .stream()
                .map(VehicleMapper.INSTANCE::entityToDto)
                .toList();

        return ResponseEntity.ok(vehicles);
    }

    @Transactional
    public ResponseEntity<VehicleDto> createVehicle(VehicleDto vehicleDto) {
        if (vehicleRepository.existsBySerialNumber(vehicleDto.getSerialNumber())) {
            throw new CreateException(Vehicle.class.getSimpleName());
        }
        Vehicle vehicle = VehicleMapper.INSTANCE.dtoToEntity(vehicleDto);
        RentalPoint rentalPoint = rentalPointRepository.findById(vehicleDto.getId())
                .orElseThrow(() -> new GetException(Vehicle.class.getSimpleName()));
        vehicle.setRentalPoint(rentalPoint);
        vehicle = vehicleRepository.save(vehicle);

        return ResponseEntity.ok(VehicleMapper.INSTANCE.entityToDto(vehicle));
    }

    @Transactional
    public ResponseEntity<VehicleDto> updateVehicle(Long id, VehicleDto vehicleDto) {
        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new GetException(Vehicle.class.getSimpleName()));
        vehicleDto.setId(id);
        VehicleMapper.INSTANCE.updateEntityFromDto(vehicleDto, existingVehicle);
        Vehicle vehicle = vehicleRepository.save(existingVehicle);

        return ResponseEntity.ok(VehicleMapper.INSTANCE.entityToDto(vehicle));
    }

    @Transactional
    public ResponseEntity<Void> deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new DeleteException(Vehicle.class.getSimpleName());
        }
        vehicleRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<VehicleInfoDto> getVehicleDetails(Long id, int page, int size) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new GetException(Vehicle.class.getSimpleName()));

        List<RentalShortInfoDto> rentals = rentalService.getAllRentalsByVehicleId(page, size, id).getBody();

        return ResponseEntity.ok(VehicleInfoMapper.INSTANCE.toVehicleInfoDto(vehicle, rentals));
    }
}
