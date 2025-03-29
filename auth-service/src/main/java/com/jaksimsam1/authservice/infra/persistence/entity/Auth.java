package com.jaksimsam1.authservice.infra.persistence.entity;

import com.fasterxml.uuid.Generators;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "AUTH")
public class Auth implements Persistable<UUID> {

    @Id
    @Column("AUTH_ID")
    private UUID authId;

    @Column("USER_ID")
    private UUID userId;

    @Email
    @NotNull(message = "Email cannot be null")
    @Column("EMAIL")
    private String email;

    @Column("PASSWORD")
    private String password;

    @Column("STATUS")
    private String status;

    @Column("ROLE")
    private String role;

    @Column("LAST_LOGIN")
    private LocalDateTime lastLogin;

    @Column("LAST_PASSWORD_CHANGE")
    private LocalDateTime lastPasswordChange;

    @Column("LAST_STATUS_MODIFIED")
    private LocalDateTime lastStatusModified;

    @Column("CREATED_AT")
    private LocalDateTime createdAt;

    @Transient
    private boolean isNew;

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
        this.isNew = true;
    }

    @Override
    public UUID getId() {
        return this.authId;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public void markPersisted() {
        this.isNew = false;
    }
}