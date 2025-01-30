package com.jaksimsam1.userservice.user.repository.query;

import com.jaksimsam1.userservice.user.entity.Users;

import java.util.List;

public interface UserQueryRepository {
    List<Users> findAllUsersInUse();
}
