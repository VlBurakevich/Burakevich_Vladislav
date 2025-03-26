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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final RentalPointRepository rentalPointRepository;
    private final RentalService rentalService;
    private final ModelRepository modelRepository;
    private final VehicleMapper vehicleMapper;
    private final VehicleInfoMapper vehicleInfoMapper;


    public VehicleListDto getVehicles(Integer page, Integer size) {
        Page<Vehicle> vehiclePage = vehicleRepository.findAll(PageRequest.of(page, size));
        List<VehicleDto> vehicles = vehiclePage.getContent().stream()
                .map(vehicleMapper::entityToDto)
                .toList();

        return new VehicleListDto(vehicles);
    }

    public VehicleListDto getVehiclesByRentalPointId(Long id, Integer page, Integer size) {
        Page<Vehicle> vehiclePage = vehicleRepository.findAllByRentalPointId(id, PageRequest.of(page, size));
        List<VehicleDto> vehicles = vehiclePage.getContent().stream()
                .map(vehicleMapper::entityToDto)
                .toList();

        return new VehicleListDto(vehicles);
    }

    public VehicleInfoDto getVehicleDetails(Long id, Integer page, Integer size) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new GetException(Vehicle.class.getSimpleName()));

        List<RentalShortInfoDto> rentals = rentalService.getAllRentalsByVehicleId(page, size, id);

        return vehicleInfoMapper.toVehicleInfoDto(vehicle, rentals);
    }

    @Transactional
    public VehicleDto createVehicle(VehicleDto vehicleDto) {
        if (vehicleRepository.existsBySerialNumber(vehicleDto.getSerialNumber())) {
            throw new CreateException(Vehicle.class.getSimpleName());
        }
        Vehicle vehicle = vehicleMapper.dtoToEntity(vehicleDto);
        RentalPoint rentalPoint = rentalPointRepository.findById(vehicleDto.getRentalPointId())
                .orElseThrow(() -> new GetException(Vehicle.class.getSimpleName()));
        vehicle.setRentalPoint(rentalPoint);
        vehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.entityToDto(vehicle);
    }

    @Transactional
    public VehicleDto updateVehicle(Long id, VehicleDto vehicleDto) {
        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new GetException(Vehicle.class.getSimpleName()));
        vehicleDto.setId(id);
        vehicleMapper.updateEntityFromDto(vehicleDto, existingVehicle);
        RentalPoint newPoint = rentalPointRepository
                .findById(vehicleDto.getRentalPointId()).orElseThrow(() -> new GetException(RentalPoint.class.getSimpleName()));
        existingVehicle.setRentalPoint(newPoint);
        Model newModel = modelRepository.findById(vehicleDto.getModelId())
                .orElseThrow(() -> new GetException(Model.class.getSimpleName()));
        existingVehicle.setModel(newModel);
        Vehicle vehicle = vehicleRepository.save(existingVehicle);

        return vehicleMapper.entityToDto(vehicle);
    }

    @Transactional
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new DeleteException(Vehicle.class.getSimpleName());
        }
        vehicleRepository.deleteById(id);
    }
}
