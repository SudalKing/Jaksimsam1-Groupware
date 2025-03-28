package com.jaksimsam1.userservice.domain.user.repository.query;

import com.jaksimsam1.userservice.infra.persistence.entity.Users;

import java.util.List;

public interface UserQueryRepository {
    List<Users> findAllUsersInUse();
}
