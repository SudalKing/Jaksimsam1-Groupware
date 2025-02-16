package com.jaksimsam1.authservice.domain.auth.service

import com.jaksimsam1.authservice.domain.auth.entity.AuthUsers

interface AuthService {
    suspend fun findByEmailAndPassword(email: String, password: String): AuthUsers
    fun findAllLikeEmail(email: String): List<AuthUsers>
//    suspend fun authenticate(, email: String, password: String)
}