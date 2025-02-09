package com.jaksimsam1.authservice.domain.auth.service

import com.jaksimsam1.authservice.domain.auth.entity.AuthUsers
import com.jaksimsam1.authservice.domain.auth.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val authRepository: AuthRepository,
): AuthService {

    override suspend fun findByEmailAndPassword(
        email: String,
        password: String
    ): AuthUsers {
        return withContext(Dispatchers.IO) {
            authRepository.findByEmailAndPassword(email, password)
        }
    }

    override fun findAllLikeEmail(
        email: String
    ): List<AuthUsers> {
        return authRepository.findAll{
            select(entity(AuthUsers::class))
                .from(entity(AuthUsers::class))
                .whereAnd(
                    path(AuthUsers::email).like("%${email}%"),
                )
        }.filterNotNull()
    }
}