package com.example.service;

import com.example.dto.vehicle.TransportTypeDto;
import com.example.entity.TransportType;
import com.example.exceptions.CreateException;
import com.example.exceptions.UpdateException;
import com.example.mapper.TransportTypeMapper;
import com.example.repository.TransportTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class TransportTypeService {

    private final TransportTypeRepository transportTypeRepository;
    private final TransportTypeMapper transportTypeMapper;

    public ResponseEntity<List<TransportTypeDto>> getTransportTypes(int page, int size) {
        Page<TransportType> transportTypePage = transportTypeRepository.findAll(PageRequest.of(page, size));
        List <TransportTypeDto> transportTypeDtos = transportTypePage.getContent()
                .stream()
                .map(transportTypeMapper::entityToDto)
                .toList();

        return ResponseEntity.ok(transportTypeDtos);
    }

    @Transactional
    public ResponseEntity<TransportTypeDto> createTransportType(TransportTypeDto transportTypeDto) {
        if (transportTypeRepository.existsByTypeName(transportTypeDto.getTypeName())) {
            throw new CreateException(TransportType.class.getSimpleName());
        }
        TransportType transportType = transportTypeMapper.dtoToEntity(transportTypeDto);
        transportType = transportTypeRepository.save(transportType);
        return ResponseEntity.ok(transportTypeMapper.entityToDto(transportType));
    }

    @Transactional
    public ResponseEntity<TransportTypeDto> updateTransportType(Long id, TransportTypeDto transportTypeDto) {
        TransportType existingType = transportTypeRepository.findById(id).
                orElseThrow(() -> new UpdateException(TransportType.class.getSimpleName()));

        transportTypeDto.setId(id);
        transportTypeMapper.updateEntityFromDto(transportTypeDto, existingType);
        TransportType transportType = transportTypeRepository.save(existingType);

        return ResponseEntity.ok(transportTypeMapper.entityToDto(transportType));
    }

    @Transactional
    public ResponseEntity<Void> deleteTransportType(Long id) {
        if (!transportTypeRepository.existsById(id)) {
            throw new UpdateException(TransportType.class.getSimpleName());
        }
        transportTypeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
