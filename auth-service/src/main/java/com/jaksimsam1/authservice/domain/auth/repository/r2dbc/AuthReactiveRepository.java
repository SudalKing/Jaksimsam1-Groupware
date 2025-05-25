package com.jaksimsam1.authservice.domain.auth.repository.r2dbc;

import com.jaksimsam1.authservice.infra.persistence.entity.Auth;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AuthReactiveRepository extends ReactiveCrudRepository<Auth, UUID> {
    Mono<Auth> findByEmail(String email);
    Mono<Auth> findByEmailAndPassword(String email, String password);
    Mono<Void> deleteByUserId(UUID userId);
}
