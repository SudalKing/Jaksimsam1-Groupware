package com.jaksimsam1.authservice.domain.auth.service;

import com.jaksimsam1.authservice.domain.auth.model.request.AuthCreateRequest;
import com.jaksimsam1.authservice.domain.auth.model.request.RefreshTokenRequest;
import com.jaksimsam1.commondto.model.response.ApiResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
    // 인증 정보 생성
    Mono<ApiResponse<Void>> createAuth(AuthCreateRequest request);
    // 2. 로그인
    Mono<ServerResponse> login(ServerRequest request);
    // 3. 토큰 갱신
    Mono<ServerResponse> refresh(RefreshTokenRequest refreshTokenRequest);
    // 4. 로그아웃
    Mono<Void> logout(String email);
}
