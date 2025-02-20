package com.jaksimsam1.authservice.domain.auth.repository.query.impl;

import com.jaksimsam1.authservice.domain.auth.repository.query.AuthUsersQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthUsersQueryRepositoryImpl implements AuthUsersQueryRepository {

    private final JPAQueryFactory queryFactory;


}
