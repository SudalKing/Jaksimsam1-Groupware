package com.jaksimsam1.authservice.infra.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtProvider jwtProvider;

    public String createAccessToken(String email) {
        return jwtProvider.createAccessToken(email);
    }

    public String createRefreshToken(String email) {
        return jwtProvider.createRefreshToken(email);
    }

    public String parseToken(String token) {
        return jwtProvider.parseToken(token).getSubject();
    }
}
