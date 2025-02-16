package com.jaksimsam1.authservice.domain.auth.repository.jpa

import com.jaksimsam1.authservice.domain.auth.entity.AuthUsers
import org.springframework.data.jpa.repository.JpaRepository

interface AuthJpaRepository: JpaRepository<AuthUsers, String> {
    suspend fun findByEmailAndPassword(email: String, password: String): AuthUsers
}