package com.jaksimsam1.authservice.domain.jwt.service;

import com.jaksimsam1.authservice.domain.jwt.entity.RefreshToken;
import reactor.core.publisher.Mono;

public interface RefreshTokenService {
    Mono<RefreshToken> saveRefreshToken(RefreshToken refreshToken);
    Mono<RefreshToken> findRefreshTokenByEmail(String email);
    Mono<Void> deleteRefreshToken(String email);
}
