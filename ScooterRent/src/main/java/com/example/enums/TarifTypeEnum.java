package com.example.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TarifTypeEnum {
    HOURLY, SUBSCRIPTION;

    @JsonValue
    public String getValue() {
        return name();
    }
}
