package com.jaksimsam1.authservice.domain.auth.model.request;

import lombok.Getter;

@Getter
public class RefreshTokenRequest {
    private String refreshToken;
}
