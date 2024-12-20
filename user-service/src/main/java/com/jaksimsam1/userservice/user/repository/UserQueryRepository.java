package com.jaksimsam1.userservice.user.repository;

import com.jaksimsam1.userservice.user.entity.User;

import java.util.List;

public interface UserQueryRepository {

    User findUserByUserId();
    List<User> findAllUsers();
}
