package com.jaksimsam1.authservice.domain.auth.repository.query.impl;

import com.jaksimsam1.authservice.domain.auth.repository.query.AuthQueryRepository;
import com.jaksimsam1.authservice.infra.persistence.entity.Auth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import reactor.core.publisher.Mono;

import static com.jaksimsam1.authservice.infra.persistence.jooq.Tables.AUTH;


@Slf4j
@RequiredArgsConstructor
public class AuthQueryRepositoryImpl implements AuthQueryRepository {

    private final DSLContext dsl;

    public Mono<Auth> findByEmail(String email) {
        return Mono.from(dsl.selectFrom(AUTH)
                .where(AUTH.EMAIL.eq(email)))
                .map(record -> record.into(Auth.class));
    }

}
