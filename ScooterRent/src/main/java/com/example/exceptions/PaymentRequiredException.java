package com.example.exceptions;

import java.math.BigDecimal;

public class PaymentRequiredException extends RuntimeException {
    public static final String ERROR_MESSAGE = "Required: %.2f, Available: %.2f. Insufficient funds. Please top up your balance";

    public PaymentRequiredException(BigDecimal cost, BigDecimal balance) {
        super(ERROR_MESSAGE.formatted(cost, balance));
    }
}
