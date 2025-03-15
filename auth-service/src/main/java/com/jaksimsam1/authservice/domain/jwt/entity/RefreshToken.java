package com.jaksimsam1.authservice.domain.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshToken {
    private String email;
    private String token;
}
