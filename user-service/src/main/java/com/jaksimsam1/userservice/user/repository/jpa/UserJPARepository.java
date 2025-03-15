package com.jaksimsam1.userservice.user.repository.jpa;

import com.jaksimsam1.userservice.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserJPARepository extends JpaRepository<Users, UUID> {

    Users findByUserId(@Param("userId") UUID userId);
}
