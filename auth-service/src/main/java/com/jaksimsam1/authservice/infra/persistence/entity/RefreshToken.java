package com.jaksimsam1.authservice.infra.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshToken {
    private String email;
    private String token;
}
