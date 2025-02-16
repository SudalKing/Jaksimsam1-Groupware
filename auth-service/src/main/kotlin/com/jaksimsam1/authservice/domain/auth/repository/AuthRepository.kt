package com.jaksimsam1.authservice.domain.auth.repository

import com.jaksimsam1.authservice.domain.auth.repository.jdsl.AuthJdslRepository
import com.jaksimsam1.authservice.domain.auth.repository.jpa.AuthJpaRepository

interface AuthRepository: AuthJpaRepository, AuthJdslRepository {
}