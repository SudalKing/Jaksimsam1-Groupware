package com.jaksimsam1.authservice.domain.auth.repository

import com.jaksimsam1.authservice.domain.auth.entity.AuthUsers
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface AuthRepository: JpaRepository<AuthUsers, String>, KotlinJdslJpqlExecutor {
    suspend fun findByEmailAndPassword(email: String, password: String): AuthUsers
}