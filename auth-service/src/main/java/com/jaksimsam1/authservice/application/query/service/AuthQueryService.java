package com.jaksimsam1.authservice.application.query.service;

import com.jaksimsam1.authservice.domain.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthQueryService {

    private final AuthRepository authRepository;


}
