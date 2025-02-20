package com.jaksimsam1.authservice.domain.auth.repository.jpa;

import com.jaksimsam1.authservice.domain.auth.entity.AuthUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUsersJPARepository extends JpaRepository<AuthUsers, String> {
    AuthUsers findByUserId(String userId);
    AuthUsers findByEmail(String email);
    AuthUsers findByEmailAndPassword(String email, String password);
    void deleteByUserId(String userId);
}
