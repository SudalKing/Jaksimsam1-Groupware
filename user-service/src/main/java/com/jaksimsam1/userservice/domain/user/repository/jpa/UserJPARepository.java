package com.jaksimsam1.userservice.domain.user.repository.jpa;

import com.jaksimsam1.userservice.infra.persistence.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserJPARepository extends JpaRepository<Users, UUID> {

    Users findByUserId(@Param("userId") UUID userId);
}
