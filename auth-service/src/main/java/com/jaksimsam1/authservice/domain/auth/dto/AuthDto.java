package com.jaksimsam1.authservice.domain.auth.dto;

import com.jaksimsam1.authservice.domain.auth.entity.Auth;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AuthDto {
    private UUID id;
    private String email;
    private String password;
    private String status;
    private String role;
    private LocalDateTime lastLogin;
    private LocalDateTime lastPasswordChange;
    private LocalDateTime lastStatusModified;
    private LocalDateTime createdAt;

    public AuthDto(UUID id, String email, String password, String status, String role, LocalDateTime lastLogin, LocalDateTime lastPasswordChange, LocalDateTime lastStatusModified, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
        this.lastLogin = lastLogin;
        this.lastPasswordChange = lastPasswordChange;
        this.lastStatusModified = lastStatusModified;
        this.createdAt = createdAt;
    }

    public static AuthDto of(Auth auth) {
        return new AuthDto(
                auth.getAuthId()
                , auth.getEmail()
                , auth.getPassword()
                , auth.getStatus()
                , auth.getRole()
                , auth.getLastLogin()
                , auth.getLastPasswordChange()
                , auth.getLastStatusModified()
                , auth.getCreatedAt()
        );
    }
}
