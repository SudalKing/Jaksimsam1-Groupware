package com.jaksimsam1.authservice.application.command.service;

import com.jaksimsam1.authservice.domain.auth.model.enums.Role;
import com.jaksimsam1.authservice.domain.auth.model.enums.Status;
import com.jaksimsam1.authservice.domain.auth.repository.AuthRepository;
import com.jaksimsam1.authservice.infra.persistence.entity.Auth;
import com.jaksimsam1.authservice.presentation.command.request.AuthCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthCommandServiceTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthCommandService authCommandService;

    private AuthCreateRequest request;

    @BeforeEach
    void setUp() {
        request = new AuthCreateRequest(UUID.randomUUID(), "test@example.com", "password");
    }

    @Test
    void createAuth() {
        // Given
        String encodedPassword = request.getPassword();
        when(passwordEncoder.encode(any())).thenReturn(encodedPassword);

        Auth expectedAuth = new Auth(request.getUserId(), request.getEmail(), request.getPassword(), Status.ACTIVE.getValue(), Role.USER.getValue());
        when(authRepository.save(any(Auth.class))).thenReturn(Mono.just(expectedAuth));

        // When
        Mono<Auth> result = authCommandService.createAuth(request);

        // Then
        StepVerifier.create(result)
                .assertNext(savedAuth -> {
                    assertEquals(request.getEmail(), savedAuth.getEmail());
                    assertEquals(encodedPassword, savedAuth.getPassword());
                })
                .verifyComplete();

        verify(authRepository, times(1)).save(any(Auth.class));
        verify(passwordEncoder, times(1)).encode(request.getPassword());
    }

//    @Test
    void login() {
    }

//    @Test
    void refresh() {
    }

//    @Test
    void logout() {
    }
}