package com.jaksimsam1.authservice.application.command.service;

import com.jaksimsam1.authservice.application.query.service.RefreshTokenQueryService;
import com.jaksimsam1.authservice.common.exception.InvalidPasswordException;
import com.jaksimsam1.authservice.common.model.enums.ErrorCode;
import com.jaksimsam1.authservice.common.model.response.ApiResponse;
import com.jaksimsam1.authservice.domain.auth.model.enums.Role;
import com.jaksimsam1.authservice.domain.auth.model.enums.Status;
import com.jaksimsam1.authservice.domain.auth.repository.AuthRepository;
import com.jaksimsam1.authservice.common.exception.JwtNotFoundException;
import com.jaksimsam1.authservice.infra.security.jwt.JwtService;
import com.jaksimsam1.authservice.infra.persistence.entity.Auth;
import com.jaksimsam1.authservice.presentation.command.request.AuthCreateRequest;
import com.jaksimsam1.authservice.presentation.command.request.LoginRequest;
import com.jaksimsam1.authservice.presentation.command.request.TokenRefreshRequest;
import com.jaksimsam1.authservice.presentation.command.response.LoginResponse;
import com.jaksimsam1.authservice.presentation.command.response.TokenRefreshResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthCommandService {

    private final AuthRepository authRepository;

    private final JwtService jwtService;
    private final RefreshTokenCommandService refreshTokenCommandService;
    private final RefreshTokenQueryService refreshTokenQueryService;

    private final PasswordEncoder passwordEncoder;

    public Mono<ApiResponse<Void>> createAuth(AuthCreateRequest request) {
//        Auth auth = Auth.builder()
//                .userId(request.getUserId())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .status(Status.ACTIVE.getValue())
//                .role(Role.USER.getValue())
//                .build();
//        Mono<Auth> savedAuth = authRepository.save(auth);
//        log.debug("Created auth: {}", savedAuth);
//
//        return Mono.just(ApiResponse.create());

        return Mono.fromCallable(() -> passwordEncoder.encode(request.getPassword()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(encodedPassword -> {
                    Auth auth = Auth.builder()
                            .userId(request.getUserId())
                            .email(request.getEmail())
                            .password(encodedPassword)
                            .status(Status.ACTIVE.getValue())
                            .role(Role.USER.getValue())
                            .build();

                    log.info("Creating auth: {}", auth);
                    return authRepository.save(auth)
                            .doOnNext(savedAuth -> {
                                savedAuth.markPersisted();
                                log.info("Created Auth: {}", savedAuth);
                            })
                            .doOnError(throwable -> log.error("Error creating Auth", throwable))
                            .thenReturn(ApiResponse.create());
                });
    }

    /**
     * 로그인
     * @param request
     * @return
     */
    public Mono<LoginResponse> login(LoginRequest request) {
//        return Mono.fromCallable(() -> authRepository.findByEmail(request.getEmail()))
//                .subscribeOn(Schedulers.boundedElastic())
//                .flatMap(savedAuth -> {
//                    if (!passwordEncoder.matches(request.getPassword(), savedAuth.getPassword())) {
//                        return Mono.error(new InvalidPasswordException("Invalid Password", ErrorCode.INVALID_INPUT));
//                    }
//                    String accessToken = jwtService.createAccessToken(savedAuth.getEmail());
//                    String refreshToken = jwtService.createRefreshToken(savedAuth.getEmail());
//
//                    return refreshTokenCommandService.save(request.getEmail(), refreshToken)
//                            .thenReturn(new LoginResponse(accessToken, refreshToken));
//                });
        return authRepository.findByEmail(request.getEmail())
                .switchIfEmpty(Mono.error(new InvalidPasswordException("Invalid Password", ErrorCode.INVALID_INPUT)))
                .flatMap(savedAuth ->
                        Mono.fromCallable(() -> passwordEncoder.matches(request.getPassword(), savedAuth.getPassword()))
                                .subscribeOn(Schedulers.boundedElastic())
                                .flatMap(matches -> {
                                    if (!matches) {
                                        return Mono.error(new InvalidPasswordException("Invalid Password", ErrorCode.INVALID_INPUT));
                                    }
                                    String accessToken = jwtService.createAccessToken(savedAuth.getEmail());
                                    String refreshToken = jwtService.createRefreshToken(savedAuth.getEmail());

                                    return refreshTokenCommandService.save(request.getEmail(), refreshToken)
                                            .thenReturn(new LoginResponse(accessToken, refreshToken));
                                })
                );
    }

    /**
     * 토큰 갱신
     * @param request
     * @return
     */
    public Mono<TokenRefreshResponse> refresh(TokenRefreshRequest request) {
        return refreshTokenQueryService.findRefreshTokenByEmail(jwtService.parseToken(request.getRefreshToken()))
                .filter(savedToken -> savedToken.getToken().equals(request.getRefreshToken()))
                .switchIfEmpty(Mono.error(new JwtNotFoundException("Invalid RefreshToken", ErrorCode.ENTITY_NOT_FOUND)))
                .flatMap(savedToken -> {
                    String newAccessToken = jwtService.createAccessToken(savedToken.getEmail());

                    return Mono.just(new TokenRefreshResponse(newAccessToken));
                });
    }

    /**
     * 로그아웃
     * @param email
     * @return
     */
    public Mono<Void> logout(String email) {
        return refreshTokenCommandService.delete(email);
    }
}
