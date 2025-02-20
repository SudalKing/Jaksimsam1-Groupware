package com.jaksimsam1.authservice.domain.auth.service;

import com.jaksimsam1.authservice.domain.auth.entity.AuthUsers;
import com.jaksimsam1.authservice.domain.auth.model.request.AuthUsersCreateRequest;

public interface AuthUsersService {
    void save(AuthUsersCreateRequest request);
    AuthUsers findByEmail(String email);
    AuthUsers findByEmailAndPassword(String email, String password);
}
