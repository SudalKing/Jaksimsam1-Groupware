package com.jaksimsam1.userservice.user.repository;

import com.jaksimsam1.userservice.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserQueryRepositoryImpl implements UserQueryRepository{

    private JPAQueryFactory queryFactory;

    @Override
    public User findUserByUserId() {
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        return List.of();
    }
}
