package com.jaksimsam1.authservice.domain.auth.model.enums;

import lombok.Getter;

@Getter
public enum Role {
    GUEST("GUEST"),
    USER("USER"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN"),
    NOMATCH("NOMATCH")
    ;

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public static String fromValue(String value) {
        for (Role role : values()) {
            if (role.value.equals(value)) {
                return role.value;
            }
        }
        return NOMATCH.value;
    }
}
