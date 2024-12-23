package com.jaksimsam1.userservice.user.repository.query;

import com.jaksimsam1.userservice.user.entity.User;

import java.util.List;

public interface UserQueryRepository {
    List<User> findAllUsersInUse();
}
