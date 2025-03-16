package com.example.exceptions;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {

    public static final String ERROR_MESSAGE = "Insufficient balance, minimal cost: %s";

    public InsufficientBalanceException(BigDecimal minimalCost) {
        super(ERROR_MESSAGE.formatted(minimalCost));
    }
}
