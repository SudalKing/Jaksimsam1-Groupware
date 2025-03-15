package com.jaksimsam1.authservice.domain.jwt.service.impl;

import com.jaksimsam1.authservice.domain.jwt.entity.RefreshToken;
import com.jaksimsam1.authservice.domain.jwt.exception.JwtNotFoundException;
import com.jaksimsam1.authservice.domain.jwt.service.RefreshTokenService;
import com.jaksimsam1.commondto.model.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    private static final Duration REFRESH_TOKEN_EXPIRE = Duration.ofDays(7);

    @Override
    public Mono<RefreshToken> saveRefreshToken(RefreshToken refreshToken) {
        return reactiveRedisTemplate.opsForValue()
                .set(refreshToken.getEmail(), refreshToken, REFRESH_TOKEN_EXPIRE)
                .thenReturn(refreshToken);
    }

    @Override
    public Mono<RefreshToken> findRefreshTokenByEmail(String email) {
        return reactiveRedisTemplate.opsForValue()
                .get(email)
                .cast(RefreshToken.class)
                .switchIfEmpty(Mono.error(new JwtNotFoundException("RefreshToken Not Found", ErrorCode.ENTITY_NOT_FOUND)));
    }

    @Override
    public Mono<Void> deleteRefreshToken(String email) {
        return reactiveRedisTemplate.delete(email)
                .then();
    }
}
