package com.jaksimsam1.authservice.presentation.command.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenRefreshResponse {
    private String newAccessToken;
}
