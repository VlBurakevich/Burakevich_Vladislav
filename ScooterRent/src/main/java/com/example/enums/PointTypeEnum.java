package com.example.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PointTypeEnum {
    MAIN, SECONDARY;

    @JsonValue
    public String getValue() {
        return name();
    }
}
