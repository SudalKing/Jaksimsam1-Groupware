package com.jaksimsam1.authservice.domain.auth.repository;

import com.jaksimsam1.authservice.domain.auth.repository.r2dbc.AuthReactiveRepository;
import com.jaksimsam1.authservice.domain.auth.repository.query.AuthQueryRepository;

public interface AuthRepository extends AuthReactiveRepository, AuthQueryRepository {
}
