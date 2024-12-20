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
@Table(name = "USER")
public class User {
    @Id
    @Column(name = "USER_ID", length = 20, nullable = false)
    private String userId;

    @Column(name = "USERNAME", length = 50, nullable = false)
    private String username;

    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    // TODO 생년월일

    @Column(name = "GENDER", nullable = false)
    private String gender;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Builder
    public User(String userId) {
        this.userId = userId;
    }

}
