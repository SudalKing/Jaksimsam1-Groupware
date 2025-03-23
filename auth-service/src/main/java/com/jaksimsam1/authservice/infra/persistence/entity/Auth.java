package com.jaksimsam1.authservice.infra.persistence.entity;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "AUTH",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"EMAIL"}, name = "UNIQUE_KEY_EMAIL")
        })
@Entity
public class Auth {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "AUTH_ID", nullable = false, updatable = false)
    private UUID authId;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "USER_ID", nullable = false, updatable = false)
    private UUID userId;

    @Email
    @NotNull(message = "Email can not be null")
    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "STATUS", length = 20, nullable = false)
    private String status;

    @Column(name = "ROLE", length = 20, nullable = false)
    private String role;

    @Column(name = "LAST_LOGIN", nullable = false)
    private LocalDateTime lastLogin;

    @Column(name = "LAST_PASSWORD_CHANGE", nullable = false)
    private LocalDateTime lastPasswordChange;

    @Column(name = "LAST_STATUS_MODIFIED", nullable = false)
    private LocalDateTime lastStatusModified;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Auth(UUID userId, String email, String password, String status, String role) {
        this.authId = Generators.timeBasedEpochGenerator().generate();
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
        this.lastLogin = LocalDateTime.now();
        this.lastPasswordChange = LocalDateTime.now();
        this.lastStatusModified = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
