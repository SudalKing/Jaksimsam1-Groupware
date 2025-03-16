package com.jaksimsam1.userservice.presentation.command.request;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    private String username;
    private String email;
    private String password;
    private String tel;
    private String gender;
}
