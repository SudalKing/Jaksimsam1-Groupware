package com.jaksimsam1.authservice.domain.auth.dto

import com.jaksimsam1.authservice.domain.auth.entity.AuthUsers
import java.time.LocalDateTime

data class AuthUserDto(
    val id: String,
    val email: String,
    val password: String,
    val role: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {

    companion object {
        operator fun invoke(authUsers: AuthUsers) =
            with(authUsers) {
                AuthUserDto(
                    id = id,
                    email = email,
                    password = password,
                    role = role,
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                )
            }
    }
}