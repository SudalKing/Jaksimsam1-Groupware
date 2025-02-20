package com.jaksimsam1.authservice.domain.auth.service.impl;

import com.fasterxml.uuid.Generators;
import com.jaksimsam1.authservice.domain.auth.entity.AuthUsers;
import com.jaksimsam1.authservice.domain.auth.model.enums.Role;
import com.jaksimsam1.authservice.domain.auth.model.enums.Status;
import com.jaksimsam1.authservice.domain.auth.model.request.AuthUsersCreateRequest;
import com.jaksimsam1.authservice.domain.auth.repository.AuthUsersRepository;
import com.jaksimsam1.authservice.domain.auth.service.AuthUsersService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Setter
public class AuthUsersServiceImpl implements AuthUsersService {

    private final AuthUsersRepository authUsersRepository;

    @Override
    public void save(AuthUsersCreateRequest request) {
        // 1. UUID 생성
        UUID uuidV7 = Generators.timeBasedEpochGenerator().generate();
        // 2. Role 세팅
        Role role = Role.fromValue(request.getRole());
        // 3. AuthUsers 세팅
        AuthUsers authUsers = AuthUsers.builder()
                .userId(request.getUserId())
                .email(request.getEmail())
                .password(request.getPassword())
                .status(Status.ACTIVE)
                .role(role)
                .build();
        // 4. 저장
        authUsersRepository.save(authUsers);
    }

    @Override
    public AuthUsers findByEmail(String email) {
        return null;
    }

    @Override
    public AuthUsers findByEmailAndPassword(String email, String password) {
        return null;
    }
}
