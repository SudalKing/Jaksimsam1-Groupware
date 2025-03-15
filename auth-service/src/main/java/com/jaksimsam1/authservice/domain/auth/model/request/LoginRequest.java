package com.jaksimsam1.authservice.domain.auth.model.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LoginRequest {
    private String email;
    private String password;
}
