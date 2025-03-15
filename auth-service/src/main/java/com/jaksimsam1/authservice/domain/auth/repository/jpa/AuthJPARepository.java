package com.jaksimsam1.authservice.domain.auth.repository.jpa;

import com.jaksimsam1.authservice.domain.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthJPARepository extends JpaRepository<Auth, UUID> {
    Auth findByEmail(String email);
    Auth findByEmailAndPassword(String email, String password);
    void deleteByUserId(UUID userId);
}
