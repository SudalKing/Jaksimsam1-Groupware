package com.jaksimsam1.authservice.domain.auth.repository;

import com.jaksimsam1.authservice.domain.auth.repository.jpa.AuthJPARepository;
import com.jaksimsam1.authservice.domain.auth.repository.query.AuthQueryRepository;

public interface AuthRepository extends AuthJPARepository, AuthQueryRepository {
}
