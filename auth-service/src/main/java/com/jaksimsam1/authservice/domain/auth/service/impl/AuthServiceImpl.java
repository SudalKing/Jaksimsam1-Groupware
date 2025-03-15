package com.jaksimsam1.authservice.domain.auth.service.impl;

import com.jaksimsam1.authservice.domain.auth.entity.Auth;
import com.jaksimsam1.authservice.domain.auth.exception.AuthNotFoundException;
import com.jaksimsam1.authservice.domain.auth.model.enums.Role;
import com.jaksimsam1.authservice.domain.auth.model.enums.Status;
import com.jaksimsam1.authservice.domain.auth.model.request.AuthCreateRequest;
import com.jaksimsam1.authservice.domain.auth.model.request.LoginRequest;
import com.jaksimsam1.authservice.domain.auth.model.request.RefreshTokenRequest;
import com.jaksimsam1.authservice.domain.auth.repository.AuthRepository;
import com.jaksimsam1.authservice.domain.auth.service.AuthService;
import com.jaksimsam1.authservice.domain.jwt.entity.RefreshToken;
import com.jaksimsam1.authservice.domain.jwt.provider.JwtProvider;
import com.jaksimsam1.authservice.domain.jwt.service.RefreshTokenService;
import com.jaksimsam1.commondto.model.enums.ErrorCode;
import com.jaksimsam1.commondto.model.response.ApiResponse;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Override
    public Mono<ApiResponse<Void>> createAuth(AuthCreateRequest request) {
        Auth auth = Auth.builder()
                .userId(request.getUserId())
                .email(request.getEmail())
                .password(request.getPassword())
                .status(Status.ACTIVE.getValue())
                .role(Role.USER.getValue())
                .build();
        Auth savedAuth = authRepository.save(auth);
        log.debug("Created auth: {}", savedAuth);

        return Mono.just(ApiResponse.create());
    }

    /**
     * 로그인
     * @param request Email / Password
     * @return Mono<ServerResponse> 헤더에 AccessToken, RefreshToken 전달
     */
    @Override
    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(loginRequest ->
                    Mono.fromCallable(() -> authRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword()))
                            .subscribeOn(Schedulers.boundedElastic())
                            .flatMap(auth -> {
                                String accessToken = jwtProvider.createAccessToken(auth.getEmail());
                                String refreshToken = jwtProvider.createRefreshToken(auth.getEmail());

                                return refreshTokenService.saveRefreshToken(new RefreshToken(auth.getEmail(), refreshToken))
                                        .flatMap(savedRefreshToken -> ServerResponse.ok()
                                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                                .header("Refresh-Token", "Bearer " + refreshToken)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(ApiResponse.create()));
                            })
                ).onErrorResume(e -> ServerResponse.status(ErrorCode.UNAUTHORIZED.getStatus())
                        .bodyValue(ApiResponse.error())
                ).log();
    }

    /**
     * 토큰 갱신
     */
    @Override
    public Mono<ServerResponse> refresh(RefreshTokenRequest refreshTokenRequest) {
        String email = jwtProvider.parseToken(refreshTokenRequest.getRefreshToken()).getSubject();

        return refreshTokenService.findRefreshTokenByEmail(email)
                .filter(savedToken -> savedToken.getToken().equals(refreshTokenRequest.getRefreshToken()))
                .switchIfEmpty(Mono.error(new JwtException("Invalid RefreshToken")))
                .flatMap(savedToken -> {
                    String newAccessToken = jwtProvider.createAccessToken(savedToken.getEmail());
                    return ServerResponse.ok()
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken)
                            .bodyValue(ApiResponse.read(savedToken));
                });
    }

    /**
     * 로그아웃
     * @param email Email 로 Refresh 토큰 삭제
     */
    @Override
    public Mono<Void> logout(String email) {
        return refreshTokenService.deleteRefreshToken(email);
    }
}
