package com.jaksimsam1.authservice.domain.auth.repository.jdsl.impl

import com.jaksimsam1.authservice.domain.auth.entity.AuthUsers
import com.jaksimsam1.authservice.domain.auth.repository.jdsl.AuthJdslRepository
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.stereotype.Service

@Service
class AuthJdslRepositoryImpl(
    private val kotlinJdslJpqlExecutor: KotlinJdslJpqlExecutor,
): AuthJdslRepository {

    override fun findAllLikeEmail(
        email: String,
    ): List<AuthUsers> {
        return kotlinJdslJpqlExecutor.findAll {
            select(entity(AuthUsers::class))
                .from(entity(AuthUsers::class))
                .whereAnd(
                    path(AuthUsers::email).like("%${email}%"),
                )
        }.filterNotNull()
    }
}