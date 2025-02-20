package com.jaksimsam1.authservice.domain.auth.entity;

import com.fasterxml.uuid.Generators;
import com.jaksimsam1.authservice.domain.auth.model.enums.Role;
import com.jaksimsam1.authservice.domain.auth.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "AUTH_USERS",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"EMAIL"}, name = "UNIQUE_KEY_EMAIL")
        })
@Entity
public class AuthUsers {

    @Id
    @Column(name = "ID", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id = Generators.timeBasedEpochGenerator().generate();

    @Column(name = "USER_NAME", nullable = false, updatable = false)
    private String userId;

    @Email
    @NotNull(message = "Email can not be null")
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private Role role;

    @LastModifiedDate
    @Column(name = "LAST_LOGIN", nullable = false)
    private LocalDateTime lastLogin;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public AuthUsers(String userId, String email, String password, Status status, Role role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
    }
}
