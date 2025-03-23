package com.jaksimsam1.authservice.presentation.command.request;

import lombok.Getter;

@Getter
public class TokenRefreshRequest {
    private String refreshToken;
}
