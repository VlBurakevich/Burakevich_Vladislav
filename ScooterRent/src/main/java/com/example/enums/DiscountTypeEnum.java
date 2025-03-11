package com.example.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DiscountTypeEnum {
    PERCENTAGE, FIXED;

    @JsonValue
    public String getValue() {
        return name();
    }
}
