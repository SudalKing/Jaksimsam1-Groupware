package com.jaksimsam1.authservice.domain.auth.entity

import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Table(name = "AUTH_USERS")
data class AuthUsers(

    @Id
    val id: String,

    @Email
    @NotNull(message = "Email can not be null")
    @Column(name = "EMAIL", nullable = false)
    val email: String,

    @NotNull(message = "Password can not be null")
    @Column(name = "PASSWORD", nullable = false)
    val password: String,

    @Column(name = "ROLE", nullable = false)
    val role: String,

    @CreatedDate
    @Column(name = "CREATED_AT")
    val createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    val updatedAt: LocalDateTime? = null
)
