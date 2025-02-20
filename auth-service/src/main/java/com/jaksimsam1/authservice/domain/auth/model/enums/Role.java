package com.jaksimsam1.authservice.domain.auth.model.enums;

import lombok.Getter;

@Getter
public enum Role {
    GUEST("guest"),
    USER("user"),
    MANAGER("manager"),
    ADMIN("admin"),
    NOMATCH("nomatch")
    ;

    private String value;

    Role(String value) {
        this.value = value;
    }

    public static Role fromValue(String value) {
        for (Role role : values()) {
            if (role.value.equals(value)) {
                return role;
            }
        }
        return NOMATCH;
    }
}
