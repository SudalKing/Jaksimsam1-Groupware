package com.jaksimsam1.userservice.user.entity;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "USERS",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"TEL"}, name = "UNIQUE_KEY_TEL")
        })
public class Users {

    @Id
    @Column(name = "USER_ID", nullable = false, updatable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID userId;

    @Column(name = "USERNAME", length = 50, nullable = false)
    private String username;

    @Column(name = "TEL", length = 100, nullable = false)
    private String tel;

    // TODO 생년월일

    @Column(name = "GENDER", length = 10, nullable = false)
    private String gender;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "USE_YN", length = 1, nullable = false)
    private String useYn;

    @Column(name = "CREATED_AT",  nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Users(String username, String tel, String gender, String useYn) {
        this.userId = Generators.timeBasedEpochGenerator().generate();;
        this.username = username;
        this.tel = tel;
        this.gender = gender;
        this.useYn = useYn;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
