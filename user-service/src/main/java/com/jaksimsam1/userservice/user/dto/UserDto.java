package com.jaksimsam1.userservice.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.jaksimsam1.userservice.user.entity.Users;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserDto {
    private UUID userId;
    private String username;
    private String email;
    private String gender;
    private String useYn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;

    @Builder
    public UserDto(UUID userId, String username, String email, String gender, String useYn, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.useYn = useYn;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserDto of(Users users) {
        return UserDto.builder()
                .userId(users.getUserId())
                .username(users.getUsername())
                .email(users.getTel())
                .gender(users.getGender())
                .useYn(users.getUseYn())
                .createdAt(users.getCreatedAt())
                .updatedAt(users.getUpdatedAt())
                .build();
    }
}
