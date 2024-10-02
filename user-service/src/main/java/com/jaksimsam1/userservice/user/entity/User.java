package com.jaksimsam1.userservice.user.entity;

import com.jaksimsam1.userservice.user.dto.UserDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "TBL_USER")
public class User {
    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Builder
    public User(String userId) {
        this.userId = userId;
    }

    // User -> UserDto
    public UserDto toUserDto() {
        return UserDto.builder()
                .userId(userId)
                .build();
    }
}
