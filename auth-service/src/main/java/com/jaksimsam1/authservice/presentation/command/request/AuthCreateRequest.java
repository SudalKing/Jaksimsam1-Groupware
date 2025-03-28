package com.jaksimsam1.authservice.presentation.command.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@ToString
@Getter
public class AuthCreateRequest {
    private UUID userId;
    private String email;
    private String password;
}
