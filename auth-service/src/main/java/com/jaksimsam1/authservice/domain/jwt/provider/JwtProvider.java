package com.jaksimsam1.authservice.domain.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Getter
@Component
public class JwtProvider {

    @Value("${auth.jwt.access-token-expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;
    @Value("${auth.jwt.refresh-token-expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME;

    private final SecretKey secretKey;

    public JwtProvider(@Value("${auth.jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String email) {
        Claims claims = Jwts.claims().subject(email).build();
//        claims.put("role", role);
        return buildToken(claims, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String createRefreshToken(String email) {
        Claims claims = Jwts.claims().subject(email).build();
        return buildToken(claims, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public Claims parseToken(String token) {
        return this.getJws(token).getPayload();
    }

    private Jws<Claims> getJws(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
    }

    private String buildToken(Claims claims, long expiration) {
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(secretKey)
                .compact();
    }
}
