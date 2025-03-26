package com.example.controller;

import com.example.dto.rental.RentalPointDto;
import com.example.dto.rental.RentalPointHierarchyDto;
import com.example.dto.rental.RentalPointHierarchyListDto;
import com.example.dto.rental.RentalPointInfoDto;
import com.example.dto.rental.RentalPointListDto;
import com.example.enums.PointTypeEnum;
import com.example.service.RentalPointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalPointControllerTest {

    @InjectMocks
    private RentalPointController rentalPointController;

    @Mock
    private RentalPointService rentalPointService;

    @Test
    void testGetRentalPoints() {
        RentalPointDto rentalPointDto = new RentalPointDto();
        rentalPointDto.setId(1L);
        rentalPointDto.setPointName("Точка 1");
        rentalPointDto.setCapacity(10);
        rentalPointDto.setPointType(PointTypeEnum.MAIN);
        rentalPointDto.setParentPointId(2L);

        List<RentalPointDto> rentalPointList = Collections.singletonList(rentalPointDto);
        RentalPointListDto rentalPointListDto = new RentalPointListDto(rentalPointList);

        when(rentalPointService.getRentalPoints(0, 10)).thenReturn(rentalPointListDto);

        RentalPointListDto response = rentalPointController.getRentalPoints(0, 10);

        assertEquals(rentalPointListDto, response);
        verify(rentalPointService, times(1)).getRentalPoints(0, 10);
    }

    @Test
    void testGetTopLevelRentalPoints() {
        RentalPointDto mainRentalPoint = new RentalPointDto();
        mainRentalPoint.setId(1L);
        mainRentalPoint.setPointName("Главная точка");
        mainRentalPoint.setCapacity(20);
        mainRentalPoint.setPointType(PointTypeEnum.MAIN);
        mainRentalPoint.setParentPointId(null);

        RentalPointDto secondaryRentalPoint = new RentalPointDto();
        secondaryRentalPoint.setId(2L);
        secondaryRentalPoint.setPointName("Вторичная точка");
        secondaryRentalPoint.setCapacity(10);
        secondaryRentalPoint.setPointType(PointTypeEnum.SECONDARY);
        secondaryRentalPoint.setParentPointId(1L);

        RentalPointHierarchyDto hierarchyDto = new RentalPointHierarchyDto();
        hierarchyDto.setMainRentalPoint(mainRentalPoint);
        hierarchyDto.setSecondaryRentalPoints(Collections.singletonList(secondaryRentalPoint));

        List<RentalPointHierarchyDto> hierarchyList = Collections.singletonList(hierarchyDto);
        RentalPointHierarchyListDto hierarchyListDto = new RentalPointHierarchyListDto(hierarchyList);

        when(rentalPointService.getTopLevelRentalPoints(0, 5)).thenReturn(hierarchyListDto);

        RentalPointHierarchyListDto response = rentalPointController.getTopLevelRentalPoints(0, 5);

        assertEquals(hierarchyListDto, response);
        verify(rentalPointService, times(1)).getTopLevelRentalPoints(0, 5);
    }

    @Test
    void testGetRentalPointInfoById() {
        Long rentalPointId = 1L;
        RentalPointInfoDto rentalPointInfoDto = new RentalPointInfoDto();
        rentalPointInfoDto.setId(rentalPointId);
        rentalPointInfoDto.setPointName("Точка 1");
        rentalPointInfoDto.setPointType(PointTypeEnum.MAIN);

        when(rentalPointService.getRentalPointInfoById(rentalPointId)).thenReturn(rentalPointInfoDto);

        RentalPointInfoDto response = rentalPointController.getRentalPointInfoById(rentalPointId);

        assertEquals(rentalPointInfoDto, response);
        verify(rentalPointService, times(1)).getRentalPointInfoById(rentalPointId);
    }

    @Test
    void testCreateRentalPoint() {
        RentalPointDto rentalPointDto = new RentalPointDto();
        rentalPointDto.setPointName("Новая точка");
        rentalPointDto.setCapacity(15);
        rentalPointDto.setPointType(PointTypeEnum.MAIN);
        rentalPointDto.setParentPointId(2L);

        when(rentalPointService.createRentalPoint(rentalPointDto)).thenReturn(rentalPointDto);

        RentalPointDto response = rentalPointController.createRentalPoint(rentalPointDto);

        assertEquals(rentalPointDto, response);
        verify(rentalPointService, times(1)).createRentalPoint(rentalPointDto);
    }

    @Test
    void testUpdateRentalPoint() {
        Long rentalPointId = 1L;
        RentalPointDto rentalPointDto = new RentalPointDto();
        rentalPointDto.setPointName("Обновленная точка");
        rentalPointDto.setCapacity(20);
        rentalPointDto.setPointType(PointTypeEnum.MAIN);
        rentalPointDto.setParentPointId(2L);

        when(rentalPointService.updateRentalPoint(rentalPointId, rentalPointDto)).thenReturn(rentalPointDto);

        RentalPointDto response = rentalPointController.updateRentalPoint(rentalPointId, rentalPointDto);

        assertEquals(rentalPointDto, response);
        verify(rentalPointService, times(1)).updateRentalPoint(rentalPointId, rentalPointDto);
    }

    @Test
    void testDeleteRentalPoint() {
        Long rentalPointId = 1L;
        rentalPointService.deleteRentalPoint(rentalPointId);

        verify(rentalPointService, times(1)).deleteRentalPoint(rentalPointId);
    }
}
