package com.jaksimsam1.userservice.user.dto;

import com.jaksimsam1.userservice.user.entity.User;
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

    public static UserDto of(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .build();
    }
}
