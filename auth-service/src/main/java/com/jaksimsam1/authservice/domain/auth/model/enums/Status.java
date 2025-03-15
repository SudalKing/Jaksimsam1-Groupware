package com.jaksimsam1.authservice.domain.auth.model.enums;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("ACTIVE"),
    LOCKED("LOCKED"),
    DELETED("DELETED"),
    INACTIVE("INACTIVE"),
    ERROR("ERROR");
    ;

    Status(String value) {
        this.value = value;
    }

    private final String value;

    public static String fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.value.equals(value))
                return status.value;
        }

        return ERROR.value;
    }
}
