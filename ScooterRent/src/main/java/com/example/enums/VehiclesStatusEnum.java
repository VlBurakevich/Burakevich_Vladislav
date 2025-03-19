package com.example.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VehiclesStatusEnum {
    AVAILABLE, RENTED, CHARGING;

    @JsonValue
    public String getValue() {
        return name();
    }
}
