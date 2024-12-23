package com.jaksimsam1.userservice.user.dto;

import com.jaksimsam1.commondto.dto.Gender;
import com.jaksimsam1.userservice.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserDto {
    private String userId;
    private String username;
    private String email;
    private String password;
    private Gender gender;
    private String status;
    private String useYn;

    @Builder
    public UserDto(String userId, String username, String email, String password, Gender gender, String status, String useYn) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.status = status;
        this.useYn = useYn;
    }

    public static UserDto of(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .gender(user.getGender())
                .status(user.getStatus())
                .useYn(user.getUseYn())
                .build();
    }
}
