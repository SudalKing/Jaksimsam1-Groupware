package com.jaksimsam1.userservice.user.repository.query;

import com.jaksimsam1.userservice.user.entity.QUser;
import com.jaksimsam1.userservice.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> findAllUsersInUse() {
        return queryFactory
                .selectFrom(QUser.user)
                .where(QUser.user.useYn.eq("Y"))
                .fetch();
    }
}
