package com.example.service;

import com.example.dto.rental.RentalPointDto;
import com.example.dto.rental.RentalPointHierarchyDto;
import com.example.dto.rental.RentalPointInfoDto;
import com.example.entity.RentalPoint;
import com.example.entity.Vehicle;
import com.example.enums.PointTypeEnum;
import com.example.exceptions.CreateException;
import com.example.exceptions.DeleteException;
import com.example.exceptions.GetException;
import com.example.exceptions.UpdateException;
import com.example.mapper.RentalPointInfoMapper;
import com.example.mapper.RentalPointMapper;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalPointServiceTest {

    @InjectMocks
    private RentalPointService rentalPointService;

    @Mock
    private RentalPointRepository rentalPointRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private RentalPointMapper rentalPointMapper;

    @Mock
    private RentalPointInfoMapper rentalPointInfoMapper;

    @Test
    void testGetRentalPoints() {
        RentalPoint rentalPoint = new RentalPoint();
        rentalPoint.setId(1L);
        rentalPoint.setPointName("Test Point");
        rentalPoint.setPointType(PointTypeEnum.MAIN);

        RentalPointDto rentalPointDto = new RentalPointDto();
        rentalPointDto.setId(1L);
        rentalPointDto.setPointName("Test Point");
        rentalPointDto.setPointType(PointTypeEnum.MAIN);

        Page<RentalPoint> rentalPointPage = new PageImpl<>(Collections.singletonList(rentalPoint));
        when(rentalPointRepository.findAll(any(PageRequest.class))).thenReturn(rentalPointPage);
        when(rentalPointMapper.entityToDto(rentalPoint)).thenReturn(rentalPointDto);

        ResponseEntity<List<RentalPointDto>> response = rentalPointService.getRentalPoints(0, 10);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(rentalPointDto, response.getBody().getFirst());

        verify(rentalPointRepository, times(1)).findAll(any(PageRequest.class));
        verify(rentalPointMapper, times(1)).entityToDto(rentalPoint);
    }

    @Test
    void testGetTopLevelRentalPoints() {
        RentalPoint mainPoint = new RentalPoint();
        mainPoint.setId(1L);
        mainPoint.setPointName("Main Point");
        mainPoint.setPointType(PointTypeEnum.MAIN);

        RentalPoint secondaryPoint = new RentalPoint();
        secondaryPoint.setId(2L);
        secondaryPoint.setPointName("Secondary Point");
        secondaryPoint.setPointType(PointTypeEnum.SECONDARY);
        secondaryPoint.setParentPoint(mainPoint);

        RentalPointDto mainDto = new RentalPointDto();
        mainDto.setId(1L);
        mainDto.setPointName("Main Point");
        mainDto.setPointType(PointTypeEnum.MAIN);

        RentalPointDto secondaryDto = new RentalPointDto();
        secondaryDto.setId(2L);
        secondaryDto.setPointName("Secondary Point");
        secondaryDto.setPointType(PointTypeEnum.SECONDARY);

        Page<RentalPoint> mainPointsPage = new PageImpl<>(Collections.singletonList(mainPoint));
        when(rentalPointRepository.findRentalPointsByPointType(any(PageRequest.class), eq(PointTypeEnum.MAIN)))
                .thenReturn(mainPointsPage);
        when(rentalPointRepository.findRentalPointsByParentPointId(mainPoint.getId())).thenReturn(Collections.singletonList(secondaryPoint));
        when(rentalPointMapper.entityToDto(mainPoint)).thenReturn(mainDto);
        when(rentalPointMapper.toDtoList(Collections.singletonList(secondaryPoint))).thenReturn(Collections.singletonList(secondaryDto));

        ResponseEntity<List<RentalPointHierarchyDto>> response = rentalPointService.getTopLevelRentalPoints(0, 10);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(mainDto, response.getBody().getFirst().getMainRentalPoint());
        assertEquals(secondaryDto, response.getBody().getFirst().getSecondaryRentalPoints().getFirst());

        verify(rentalPointRepository, times(1)).findRentalPointsByPointType(any(PageRequest.class), eq(PointTypeEnum.MAIN));
        verify(rentalPointRepository, times(1)).findRentalPointsByParentPointId(mainPoint.getId());
        verify(rentalPointMapper, times(1)).entityToDto(mainPoint);
        verify(rentalPointMapper, times(1)).toDtoList(Collections.singletonList(secondaryPoint));
    }

    @Test
    void testGetRentalPointInfoById() {
        RentalPoint rentalPoint = new RentalPoint();
        rentalPoint.setId(1L);
        rentalPoint.setPointName("Test Point");
        rentalPoint.setPointType(PointTypeEnum.MAIN);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setRentalPoint(rentalPoint);

        RentalPointInfoDto rentalPointInfoDto = new RentalPointInfoDto();
        rentalPointInfoDto.setId(1L);
        rentalPointInfoDto.setPointName("Test Point");
        rentalPointInfoDto.setPointType(PointTypeEnum.MAIN);

        when(rentalPointRepository.findById(1L)).thenReturn(Optional.of(rentalPoint));
        when(vehicleRepository.findAllByRentalPointId(1L)).thenReturn(Collections.singletonList(vehicle));
        when(rentalPointInfoMapper.toRentalPointInfoDto(rentalPoint, Collections.singletonList(vehicle))).thenReturn(rentalPointInfoDto);

        ResponseEntity<RentalPointInfoDto> response = rentalPointService.getRentalPointInfoById(1L);

        assertNotNull(response.getBody());
        assertEquals(rentalPointInfoDto, response.getBody());

        verify(rentalPointRepository, times(1)).findById(1L);
        verify(vehicleRepository, times(1)).findAllByRentalPointId(1L);
        verify(rentalPointInfoMapper, times(1)).toRentalPointInfoDto(rentalPoint, Collections.singletonList(vehicle));
    }

    @Test
    void testGetRentalPointInfoById_NotFound() {
        when(rentalPointRepository.findById(1L)).thenReturn(Optional.empty());

        GetException exception = assertThrows(GetException.class, () -> rentalPointService.getRentalPointInfoById(1L));
        assertTrue(exception.getMessage().contains(RentalPoint.class.getSimpleName()));

        verify(rentalPointRepository, times(1)).findById(1L);
        verify(vehicleRepository, never()).findAllByRentalPointId(any());
    }

    @Test
    void testCreateRentalPoint_Success() {
        RentalPointDto rentalPointDto = new RentalPointDto();
        rentalPointDto.setPointName("Test Point");
        rentalPointDto.setPointType(PointTypeEnum.MAIN);

        RentalPoint rentalPoint = new RentalPoint();
        rentalPoint.setPointName("Test Point");
        rentalPoint.setPointType(PointTypeEnum.MAIN);

        when(rentalPointRepository.existsByPointName(rentalPointDto.getPointName())).thenReturn(false);
        when(rentalPointMapper.dtoToEntity(rentalPointDto)).thenReturn(rentalPoint);
        when(rentalPointRepository.save(rentalPoint)).thenReturn(rentalPoint);
        when(rentalPointMapper.entityToDto(rentalPoint)).thenReturn(rentalPointDto);

        ResponseEntity<RentalPointDto> response = rentalPointService.createRentalPoint(rentalPointDto);

        assertNotNull(response.getBody());
        assertEquals(rentalPointDto, response.getBody());

        verify(rentalPointRepository, times(1)).existsByPointName(rentalPointDto.getPointName());
        verify(rentalPointRepository, times(1)).save(rentalPoint);
        verify(rentalPointMapper, times(1)).dtoToEntity(rentalPointDto);
        verify(rentalPointMapper, times(1)).entityToDto(rentalPoint);
    }

    @Test
    void testCreateRentalPoint_AlreadyExists() {
        RentalPointDto rentalPointDto = new RentalPointDto();
        rentalPointDto.setPointName("Test Point");

        when(rentalPointRepository.existsByPointName(rentalPointDto.getPointName())).thenReturn(true);

        CreateException exception = assertThrows(CreateException.class, () -> rentalPointService.createRentalPoint(rentalPointDto));
        assertTrue(exception.getMessage().contains(RentalPoint.class.getSimpleName()));

        verify(rentalPointRepository, times(1)).existsByPointName(rentalPointDto.getPointName());
        verify(rentalPointRepository, never()).save(any());
    }

    @Test
    void testUpdateRentalPoint_Success() {
        Long id = 1L;
        RentalPointDto rentalPointDto = new RentalPointDto();
        rentalPointDto.setId(id);
        rentalPointDto.setPointName("Updated Point");
        rentalPointDto.setPointType(PointTypeEnum.MAIN);

        RentalPoint existingRentalPoint = new RentalPoint();
        existingRentalPoint.setId(id);
        existingRentalPoint.setPointName("Old Point");
        existingRentalPoint.setPointType(PointTypeEnum.MAIN);

        when(rentalPointRepository.findById(id)).thenReturn(Optional.of(existingRentalPoint));
        when(rentalPointRepository.save(existingRentalPoint)).thenReturn(existingRentalPoint);
        when(rentalPointMapper.entityToDto(existingRentalPoint)).thenReturn(rentalPointDto);

        ResponseEntity<RentalPointDto> response = rentalPointService.updateRentalPoint(id, rentalPointDto);

        assertNotNull(response.getBody());
        assertEquals(rentalPointDto, response.getBody());

        verify(rentalPointRepository, times(1)).findById(id);
        verify(rentalPointRepository, times(1)).save(existingRentalPoint);
        verify(rentalPointMapper, times(1)).entityToDto(existingRentalPoint);
    }

    @Test
    void testUpdateRentalPoint_NotFound() {
        Long id = 1L;
        RentalPointDto rentalPointDto = new RentalPointDto();
        rentalPointDto.setId(id);
        rentalPointDto.setPointName("Updated Point");

        when(rentalPointRepository.findById(id)).thenReturn(Optional.empty());

        UpdateException exception = assertThrows(UpdateException.class, () -> rentalPointService.updateRentalPoint(id, rentalPointDto));
        assertTrue(exception.getMessage().contains(RentalPoint.class.getSimpleName()));

        verify(rentalPointRepository, times(1)).findById(id);
        verify(rentalPointRepository, never()).save(any());
    }

    @Test
    void testDeleteRentalPoint_Success() {
        Long id = 1L;
        when(rentalPointRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = rentalPointService.deleteRentalPoint(id);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        verify(rentalPointRepository, times(1)).existsById(id);
        verify(rentalPointRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteRentalPoint_NotFound() {
        Long id = 1L;
        when(rentalPointRepository.existsById(id)).thenReturn(false);

        DeleteException exception = assertThrows(DeleteException.class, () -> rentalPointService.deleteRentalPoint(id));
        assertTrue(exception.getMessage().contains(RentalPoint.class.getSimpleName()));

        verify(rentalPointRepository, times(1)).existsById(id);
        verify(rentalPointRepository, never()).deleteById(any());
    }
}
