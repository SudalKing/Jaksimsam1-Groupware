package com.jaksimsam1.authservice.domain.auth.model.request;

import lombok.Getter;

@Getter
public class AuthUsersCreateRequest {
    private String userId;
    private String email;
    private String password;
    private String role;
}
