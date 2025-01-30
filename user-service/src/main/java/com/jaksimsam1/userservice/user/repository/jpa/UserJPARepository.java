package com.jaksimsam1.userservice.user.repository.jpa;

import com.jaksimsam1.userservice.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserJPARepository extends JpaRepository<Users, String> {

    Users findByUserId(@Param("userId") String userId);
}
