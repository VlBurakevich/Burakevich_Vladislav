package com.example.service;

import com.example.dto.vehicle.TransportTypeDto;
import com.example.dto.vehicle.TransportTypeListDto;
import com.example.entity.TransportType;
import com.example.exceptions.CreateException;
import com.example.exceptions.UpdateException;
import com.example.mapper.TransportTypeMapper;
import com.example.repository.TransportTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TransportTypeService {

    private final TransportTypeRepository transportTypeRepository;
    private final TransportTypeMapper transportTypeMapper;

    public TransportTypeListDto getTransportTypes(Integer page, Integer size) {
        Page<TransportType> transportTypePage = transportTypeRepository.findAll(PageRequest.of(page, size));
        List<TransportTypeDto> transportTypeDtos = transportTypePage.getContent().stream()
                .map(transportTypeMapper::entityToDto)
                .toList();

        return new TransportTypeListDto(transportTypeDtos);
    }

    @Transactional
    public TransportTypeDto createTransportType(TransportTypeDto transportTypeDto) {
        if (transportTypeRepository.existsByTypeName(transportTypeDto.getTypeName())) {
            throw new CreateException(TransportType.class.getSimpleName());
        }
        TransportType transportType = transportTypeMapper.dtoToEntity(transportTypeDto);
        transportType = transportTypeRepository.save(transportType);

        return transportTypeMapper.entityToDto(transportType);
    }

    @Transactional
    public TransportTypeDto updateTransportType(Long id, TransportTypeDto transportTypeDto) {
        TransportType existingType = transportTypeRepository.findById(id).
                orElseThrow(() -> new UpdateException(TransportType.class.getSimpleName()));

        transportTypeDto.setId(id);
        transportTypeMapper.updateEntityFromDto(transportTypeDto, existingType);
        TransportType transportType = transportTypeRepository.save(existingType);

        return transportTypeMapper.entityToDto(transportType);
    }

    @Transactional
    public void deleteTransportType(Long id) {
        if (!transportTypeRepository.existsById(id)) {
            throw new UpdateException(TransportType.class.getSimpleName());
        }
        transportTypeRepository.deleteById(id);
    }
}
