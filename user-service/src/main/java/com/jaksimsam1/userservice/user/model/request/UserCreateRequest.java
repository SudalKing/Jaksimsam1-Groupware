package com.jaksimsam1.userservice.user.model.request;

import lombok.Getter;

@Getter
public class UserCreateRequest {
    private String username;
    private String email;
    private String password;
    private String tel;
    private String gender;
}
