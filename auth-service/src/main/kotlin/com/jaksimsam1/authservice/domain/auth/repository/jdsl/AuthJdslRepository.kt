package com.jaksimsam1.authservice.domain.auth.repository.jdsl

import com.jaksimsam1.authservice.domain.auth.entity.AuthUsers

interface AuthJdslRepository {
    fun findAllLikeEmail(email: String): List<AuthUsers>
}