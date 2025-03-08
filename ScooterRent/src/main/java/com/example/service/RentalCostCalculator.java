package com.example.service;

import com.example.entity.Discount;
import com.example.entity.RentalCost;
import com.example.entity.Tarif;
import com.example.entity.TransportType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class RentalCostCalculator {

    public BigDecimal calculateTotalCost(RentalCost rentalCost) {

        if (rentalCost == null) {
            return BigDecimal.ZERO;
        }

        LocalDateTime start = rentalCost.getStartTime();
        LocalDateTime end = rentalCost.getEndTime();
        Tarif tarif = rentalCost.getTarif();

        if (start == null || end == null || tarif == null) {
            return BigDecimal.ZERO;
        }

        TransportType transportType = tarif.getTransportType();
        BigDecimal tarifBasePrice = tarif.getBasePrice();
        BigDecimal transportBasePrice = transportType != null ? transportType.getBasePrice() : BigDecimal.ZERO;

        if (tarifBasePrice == null) {
            tarifBasePrice = BigDecimal.ZERO;
        }
        if (transportBasePrice == null) {
            transportBasePrice = BigDecimal.ZERO;
        }

        BigDecimal total;

        switch (tarif.getType()) {
            case HOURLY:
                long minutes = java.time.Duration.between(start, end).toMinutes();
                total = tarifBasePrice.multiply(BigDecimal.valueOf(minutes)).add(transportBasePrice);
                break;

            case SUBSCRIPTION:
                total = tarifBasePrice.add(transportBasePrice);
                break;

            default:
                return BigDecimal.ZERO;
        }

        return applyDiscount(total, rentalCost.getDiscount());
    }

    private BigDecimal applyDiscount(BigDecimal total, Discount discount) {
        if (total == null || total.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        if (discount == null || discount.getValue() == null || discount.getType() == null) {
            return total;
        }

        BigDecimal discountValue = discount.getValue();
        BigDecimal result = switch (discount.getType()) {
            case PERCENTAGE -> {
                BigDecimal percentageDiscount = total.multiply(discountValue)
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                yield total.subtract(percentageDiscount);
            }
            case FIXED -> total.subtract(discountValue);
        };

        return result.max(BigDecimal.ZERO);
    }
}
