package com.jaksimsam1.userservice.domain.user.repository.query;

import com.jaksimsam1.userservice.infra.persistence.entity.QUsers;
import com.jaksimsam1.userservice.infra.persistence.entity.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Users> findAllUsersInUse() {
        return queryFactory
                .selectFrom(QUsers.users)
                .where(QUsers.users.useYn.eq("Y"))
                .fetch();
    }
}
