package com.jaksimsam1.commondto.dto;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("male"),
    FEMALE("female");
    ;

    private final String value;

    Gender(String value) {
        this.value = value;
    }
}