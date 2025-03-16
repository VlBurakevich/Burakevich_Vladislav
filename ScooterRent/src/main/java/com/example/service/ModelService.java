package com.example.service;

import com.example.dto.vehicle.ModelDto;
import com.example.entity.Model;
import com.example.entity.TransportType;
import com.example.exceptions.CreateException;
import com.example.exceptions.DeleteException;
import com.example.exceptions.GetException;
import com.example.exceptions.UpdateException;
import com.example.mapper.ModelMapper;
import com.example.repository.ModelRepository;
import com.example.repository.TransportTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;
    private final TransportTypeRepository transportTypeRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<List<ModelDto>> getModelVehicles(int page, int size) {
        Page<Model> modelPage = modelRepository.findAll(PageRequest.of(page, size));
        List<ModelDto> vehicles = modelPage.getContent()
                .stream()
                .map(modelMapper::entityToDto)
                .toList();

        return ResponseEntity.ok(vehicles);
    }

    @Transactional
    public ResponseEntity<ModelDto> createModelVehicle(ModelDto modelDto) {
        if (modelRepository.existsByModelName(modelDto.getModelName())) {
            throw new CreateException(Model.class.getSimpleName());
        }
        Model model = modelMapper.dtoToEntity(modelDto);
        TransportType transportType = transportTypeRepository.findById(modelDto.getTransportTypeId())
                .orElseThrow(() -> new GetException(TransportType.class.getSimpleName()));
        model.setTransportType(transportType);
        model = modelRepository.save(model);

        return ResponseEntity.ok(modelMapper.entityToDto(model));
    }

    @Transactional
    public ResponseEntity<ModelDto> updateModelVehicle(Long id, ModelDto modelDto) {
        Model existingModel = modelRepository.findById(id)
                .orElseThrow(() -> new UpdateException(Model.class.getSimpleName()));
        modelDto.setId(existingModel.getId());
        modelMapper.updateEntityFromDto(modelDto, existingModel);
        Model model = modelRepository.save(existingModel);

        return ResponseEntity.ok(modelMapper.entityToDto(model));
    }

    @Transactional
    public ResponseEntity<Void> deleteModelVehicle(Long id) {
        if (!modelRepository.existsById(id)) {
            throw new DeleteException(Model.class.getSimpleName());
        }
        modelRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
