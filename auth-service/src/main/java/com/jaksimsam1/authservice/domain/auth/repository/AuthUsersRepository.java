package com.jaksimsam1.authservice.domain.auth.repository;

import com.jaksimsam1.authservice.domain.auth.repository.jpa.AuthUsersJPARepository;
import com.jaksimsam1.authservice.domain.auth.repository.query.AuthUsersQueryRepository;

public interface AuthUsersRepository extends AuthUsersJPARepository, AuthUsersQueryRepository {
}
