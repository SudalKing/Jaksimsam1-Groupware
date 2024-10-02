package com.jaksimsam1.userservice.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserDto {
    private String userId;

    @Builder
    public UserDto(String userId) {
        this.userId = userId;
    }
}
