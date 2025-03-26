package com.example.service;

import com.example.dto.vehicle.ModelDto;
import com.example.dto.vehicle.ModelListDto;
import com.example.entity.Model;
import com.example.entity.TransportType;
import com.example.exceptions.CreateException;
import com.example.exceptions.DeleteException;
import com.example.exceptions.GetException;
import com.example.exceptions.UpdateException;
import com.example.mapper.ModelMapper;
import com.example.repository.ModelRepository;
import com.example.repository.TransportTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModelServiceTest {

    @InjectMocks
    private ModelService modelService;

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TransportTypeRepository transportTypeRepository;

    @Test
    void testGetModelVehicles() {
        Model model = new Model();
        model.setId(1L);
        model.setModelName("Test Model");
        model.setMaxSpeed(200);
        TransportType transportType = new TransportType();
        transportType.setId(1L);
        model.setTransportType(transportType);

        ModelDto modelDto = new ModelDto();
        modelDto.setId(1L);
        modelDto.setModelName("Test Model");
        modelDto.setMaxSpeed(200);
        modelDto.setTransportTypeId(1L);

        Page<Model> modelPage = new PageImpl<>(Collections.singletonList(model));
        when(modelRepository.findAll(any(PageRequest.class))).thenReturn(modelPage);
        when(modelMapper.entityToDto(model)).thenReturn(modelDto);

        ModelListDto response = modelService.getModelVehicles(0, 10);

        assertNotNull(response);
        assertEquals(1, response.getModels().size());
        assertEquals(modelDto, response.getModels().getFirst());
    }

    @Test
    void testCreateModel_Success() {
        ModelDto modelDto = new ModelDto();
        modelDto.setModelName("Test Model");
        modelDto.setMaxSpeed(200);
        modelDto.setTransportTypeId(1L);

        Model model = new Model();
        model.setModelName("Test Model");
        model.setMaxSpeed(200);

        TransportType transportType = new TransportType();
        transportType.setId(1L);

        when(modelRepository.existsByModelName(modelDto.getModelName())).thenReturn(false);
        when(modelMapper.dtoToEntity(modelDto)).thenReturn(model);
        when(transportTypeRepository.findById(modelDto.getTransportTypeId())).thenReturn(Optional.of(transportType));
        when(modelRepository.save(model)).thenReturn(model);
        when(modelMapper.entityToDto(model)).thenReturn(modelDto);

        ModelDto response = modelService.createModelVehicle(modelDto);

        assertNotNull(response);
        assertEquals(modelDto, response);
    }

    @Test
    void testCreateModel_AlreadyExist() {
        ModelDto modelDto = new ModelDto();
        modelDto.setModelName("Test Model");

        when(modelRepository.existsByModelName(modelDto.getModelName())).thenReturn(true);

        assertThrows(CreateException.class, () -> modelService.createModelVehicle(modelDto));
    }

    @Test
    void testCreateModel_TransportTypeNotFound() {
        ModelDto modelDto = new ModelDto();
        modelDto.setModelName("Test Model");
        modelDto.setTransportTypeId(1L);

        when(modelRepository.existsByModelName(modelDto.getModelName())).thenReturn(false);
        when(transportTypeRepository.findById(modelDto.getTransportTypeId())).thenReturn(Optional.empty());

        assertThrows(GetException.class, () -> modelService.createModelVehicle(modelDto));
    }

    @Test
    void testUpdateModel_Success() {
        Long id = 1L;
        ModelDto modelDto = new ModelDto();
        modelDto.setModelName("Updated Model");
        modelDto.setMaxSpeed(250);
        modelDto.setTransportTypeId(1L);

        Model existingModel = new Model();
        existingModel.setId(id);
        existingModel.setModelName("Old Model");
        existingModel.setMaxSpeed(200);
        TransportType transportType = new TransportType();
        transportType.setId(1L);
        existingModel.setTransportType(transportType);

        when(modelRepository.findById(id)).thenReturn(Optional.of(existingModel));
        when(modelRepository.save(existingModel)).thenReturn(existingModel);
        when(modelMapper.entityToDto(existingModel)).thenReturn(modelDto);

        ModelDto response = modelService.updateModelVehicle(id, modelDto);

        assertNotNull(response);
        assertEquals(modelDto, response);
    }

    @Test
    void testUpdateModel_NotFound() {
        Long id = 1L;
        ModelDto modelDto = new ModelDto();
        modelDto.setModelName("Updated Model");

        when(modelRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UpdateException.class, () -> modelService.updateModelVehicle(id, modelDto));
    }

    @Test
    void testDeleteModel_Success() {
        Long id = 1L;
        when(modelRepository.existsById(id)).thenReturn(true);

        modelService.deleteModelVehicle(id);

        verify(modelRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteModel_NotFound() {
        Long id = 1L;
        when(modelRepository.existsById(id)).thenReturn(false);

        assertThrows(DeleteException.class, () -> modelService.deleteModelVehicle(id));
    }
}