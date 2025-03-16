package com.example.service;

import com.example.entity.Discount;
import com.example.entity.RentalCost;
import com.example.entity.Tarif;
import com.example.entity.TransportType;
import com.example.enums.DiscountTypeEnum;
import com.example.enums.TarifTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RentalCostCalculatorTest {

    @InjectMocks
    private RentalCostCalculator rentalCostCalculator;

    @BeforeEach
    void setUp() {
        rentalCostCalculator = new RentalCostCalculator();
    }

    @Test
    void testCalculateTotalCost_HourlyTarif() {
        RentalCost rentalCost = new RentalCost();
        rentalCost.setStartTime(LocalDateTime.of(2023, 10, 1, 10, 0));
        rentalCost.setEndTime(LocalDateTime.of(2023, 10, 1, 11, 5));
        Tarif tarif = new Tarif();
        tarif.setType(TarifTypeEnum.HOURLY);
        tarif.setBasePrice(new BigDecimal("10.00"));
        tarif.setUnitTime(60);
        TransportType transportType = new TransportType();
        transportType.setBasePrice(new BigDecimal("5.00"));
        tarif.setTransportType(transportType);
        rentalCost.setTarif(tarif);

        BigDecimal totalCost = rentalCostCalculator.calculateTotalCost(rentalCost);

        assertEquals(new BigDecimal("30.00"), totalCost);
    }

    @Test
    void testCalculateTotalCost_SubscriptionTarif() {
        RentalCost rentalCost = new RentalCost();
        rentalCost.setStartTime(LocalDateTime.of(2023, 10, 1, 10, 0));
        rentalCost.setEndTime(LocalDateTime.of(2023, 10, 1, 11, 0));
        Tarif tarif = new Tarif();
        tarif.setType(TarifTypeEnum.SUBSCRIPTION);
        tarif.setBasePrice(new BigDecimal("100.00"));
        TransportType transportType = new TransportType();
        transportType.setBasePrice(new BigDecimal("20.00"));
        tarif.setTransportType(transportType);
        rentalCost.setTarif(tarif);

        BigDecimal totalCost = rentalCostCalculator.calculateTotalCost(rentalCost);

        assertEquals(new BigDecimal("120.00"), totalCost);
    }

    @Test
    void testApplyDiscount_Percentage() {
        BigDecimal total = new BigDecimal("100.00");
        Discount discount = new Discount();
        discount.setType(DiscountTypeEnum.PERCENTAGE);
        discount.setValue(new BigDecimal("10.00"));

        BigDecimal result = rentalCostCalculator.applyDiscount(total, discount);

        assertEquals(new BigDecimal("90.00"), result);
    }

    @Test
    void testApplyDiscount_Fixed() {
        BigDecimal total = new BigDecimal("100.00");
        Discount discount = new Discount();
        discount.setType(DiscountTypeEnum.FIXED);
        discount.setValue(new BigDecimal("15.00"));

        BigDecimal result = rentalCostCalculator.applyDiscount(total, discount);

        assertEquals(new BigDecimal("85.00"), result);
    }
}
