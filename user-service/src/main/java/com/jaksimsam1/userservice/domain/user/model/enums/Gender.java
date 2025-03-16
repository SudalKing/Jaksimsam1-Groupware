package com.jaksimsam1.userservice.domain.user.model.enums;


public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");
    ;

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public static String fromValue(String value) {
        if (value.equals(MALE.value)) return MALE.value;

        return FEMALE.value;
    }
}
