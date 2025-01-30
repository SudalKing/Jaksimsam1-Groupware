package com.jaksimsam1.userservice.user.entity;

import com.jaksimsam1.commondto.dto.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "USERS")
public class Users {
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
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "USE_YN", nullable = false)
    private String useYn;

    @Builder
    public Users(String userId) {
        this.userId = userId;
    }

}
