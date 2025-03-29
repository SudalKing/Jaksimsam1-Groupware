package com.jaksimsam1.authservice.application.query.service;

import com.jaksimsam1.authservice.common.exception.JwtNotFoundException;
import com.jaksimsam1.authservice.common.model.enums.ErrorCode;
import com.jaksimsam1.authservice.infra.persistence.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenQueryService {

    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    public Mono<RefreshToken> findRefreshTokenByEmail(String email) {
        return reactiveRedisTemplate.opsForValue()
                .get(email)
                .cast(RefreshToken.class)
                .switchIfEmpty(Mono.error(new JwtNotFoundException("RefreshToken Not Found", ErrorCode.ENTITY_NOT_FOUND)));
    }
}
