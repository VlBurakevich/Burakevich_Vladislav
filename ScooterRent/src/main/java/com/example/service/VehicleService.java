package com.example.service;

import com.example.dto.VehicleDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {


    public ResponseEntity<List<VehicleDto>> getVehicles(int page, int size) {

    }

    public ResponseEntity<VehicleDto> createVehicle(VehicleDto vehicleDto) {

    }

    public ResponseEntity<VehicleDto> updateVehicle(Long id, @Valid VehicleDto vehicleDto) {

    }

    public ResponseEntity<Void> deleteVehicle(Long id) {

    }
}
