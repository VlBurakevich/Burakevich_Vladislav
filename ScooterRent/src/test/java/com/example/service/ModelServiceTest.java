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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void testGetModel() {
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

        ResponseEntity<List<ModelDto>> response = modelService.getModelVehicles(0, 10);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(modelDto, response.getBody().getFirst());

        verify(modelRepository, times(1)).findAll(any(PageRequest.class));
        verify(modelMapper, times(1)).entityToDto(model);
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

        ResponseEntity<ModelDto> response = modelService.createModelVehicle(modelDto);

        assertNotNull(response.getBody());
        assertEquals(modelDto, response.getBody());

        verify(modelRepository, times(1)).existsByModelName(modelDto.getModelName());
        verify(transportTypeRepository, times(1)).findById(modelDto.getTransportTypeId());
        verify(modelRepository, times(1)).save(model);
        verify(modelMapper, times(1)).dtoToEntity(modelDto);
        verify(modelMapper, times(1)).entityToDto(model);
    }

    @Test
    void testCreateModel_AlreadyExist() {
        ModelDto modelDto = new ModelDto();
        modelDto.setModelName("Test Model");

        when(modelRepository.existsByModelName(modelDto.getModelName())).thenReturn(true);

        CreateException exception = assertThrows(CreateException.class, () -> modelService.createModelVehicle(modelDto));
        assertTrue(exception.getMessage().contains(Model.class.getSimpleName()));

        verify(modelRepository, times(1)).existsByModelName(modelDto.getModelName());
        verify(modelRepository, never()).save(any());
    }

    @Test
    void testCreateModel_TransportTypeNotFound() {
        ModelDto modelDto = new ModelDto();
        modelDto.setModelName("Test Model");
        modelDto.setTransportTypeId(1L);

        when(modelRepository.existsByModelName(modelDto.getModelName())).thenReturn(false);
        when(transportTypeRepository.findById(modelDto.getTransportTypeId())).thenReturn(Optional.empty());

        GetException exception = assertThrows(GetException.class, () -> modelService.createModelVehicle(modelDto));
        assertTrue(exception.getMessage().contains(TransportType.class.getSimpleName()));

        verify(modelRepository, times(1)).existsByModelName(modelDto.getModelName());
        verify(transportTypeRepository, times(1)).findById(modelDto.getTransportTypeId());
        verify(modelRepository, never()).save(any());
    }

    @Test
    void testUpdateModel_Success() {
        Long id = 1L;
        ModelDto modelDto = new ModelDto();
        modelDto.setId(id);
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

        ResponseEntity<ModelDto> response = modelService.updateModelVehicle(id, modelDto);

        assertNotNull(response.getBody());
        assertEquals(modelDto, response.getBody());

        verify(modelRepository, times(1)).findById(id);
        verify(modelRepository, times(1)).save(existingModel);
        verify(modelMapper, times(1)).entityToDto(existingModel);
    }

    @Test
    void testUpdateModel_NotFound() {
        Long id = 1L;
        ModelDto modelDto = new ModelDto();
        modelDto.setId(id);
        modelDto.setModelName("Updated Model");

        when(modelRepository.findById(id)).thenReturn(Optional.empty());

        UpdateException exception = assertThrows(UpdateException.class, () -> modelService.updateModelVehicle(id, modelDto));
        assertTrue(exception.getMessage().contains(Model.class.getSimpleName()));

        verify(modelRepository, times(1)).findById(id);
        verify(modelRepository, never()).save(any());
    }

    @Test
    void testDeleteModel_Success() {
        Long id = 1L;
        when(modelRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = modelService.deleteModelVehicle(id);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        verify(modelRepository, times(1)).existsById(id);
        verify(modelRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteModel_NotFound() {
        Long id = 1L;
        when(modelRepository.existsById(id)).thenReturn(false);

        DeleteException exception = assertThrows(DeleteException.class, () -> modelService.deleteModelVehicle(id));
        assertTrue(exception.getMessage().contains(Model.class.getSimpleName()));

        verify(modelRepository, times(1)).existsById(id);
        verify(modelRepository, never()).deleteById(any());
    }
}
