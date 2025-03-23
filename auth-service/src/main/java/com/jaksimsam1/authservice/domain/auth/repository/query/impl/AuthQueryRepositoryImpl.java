package com.jaksimsam1.authservice.domain.auth.repository.query.impl;

import com.jaksimsam1.authservice.domain.auth.repository.query.AuthQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthQueryRepositoryImpl implements AuthQueryRepository {

    private final JPAQueryFactory queryFactory;


}
