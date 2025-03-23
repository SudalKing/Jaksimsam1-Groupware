package com.jaksimsam1.authservice.presentation.command.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LoginRequest {
    private String email;
    private String password;
}
