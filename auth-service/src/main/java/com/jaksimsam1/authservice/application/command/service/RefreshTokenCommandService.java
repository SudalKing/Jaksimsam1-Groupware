package com.jaksimsam1.authservice.application.command.service;

import com.jaksimsam1.authservice.infra.persistence.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenCommandService {

    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    private static final Duration REFRESH_TOKEN_EXPIRE = Duration.ofDays(7);

    public Mono<RefreshToken> save(String email, String token) {
        RefreshToken refreshToken = new RefreshToken(email, token);

        return reactiveRedisTemplate.opsForValue()
                .set(email, refreshToken, REFRESH_TOKEN_EXPIRE)
                .thenReturn(refreshToken);
    }

    public Mono<Void> delete(String email) {
        return reactiveRedisTemplate.delete(email).then();
    }
}
