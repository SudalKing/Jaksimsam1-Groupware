package com.jaksimsam1.authservice.domain.auth.model.enums;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("active"),
    LOCKED("locked"),
    ;

    Status(String value) {
        this.value = value;
    }

    private String value;
}
