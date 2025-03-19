package com.example.service;

import com.example.entity.Discount;
import com.example.entity.RentalCost;
import com.example.entity.Tarif;
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

        BigDecimal tarifBasePrice = tarif.getBasePrice() != null ? tarif.getBasePrice() : BigDecimal.ZERO;
        BigDecimal transportBasePrice = tarif.getTransportType() != null && tarif.getTransportType().getBasePrice() != null
                ? tarif.getTransportType().getBasePrice()
                : BigDecimal.ZERO;

        BigDecimal total = switch (tarif.getType()) {
            case HOURLY -> {
                long durationMinutes = java.time.Duration.between(start, end).toMinutes();
                long units = durationMinutes / tarif.getUnitTime();
                if (durationMinutes % tarif.getUnitTime() != 0) {
                    units++;
                }
                yield tarifBasePrice.add(transportBasePrice).multiply(BigDecimal.valueOf(units));
            }
            case SUBSCRIPTION -> tarifBasePrice.add(transportBasePrice);
        };

        return applyDiscount(total, rentalCost.getDiscount());
    }

    public BigDecimal applyDiscount(BigDecimal total, Discount discount) {
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
