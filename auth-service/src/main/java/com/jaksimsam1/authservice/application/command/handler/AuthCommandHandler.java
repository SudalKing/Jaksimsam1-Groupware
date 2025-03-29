package com.jaksimsam1.authservice.application.command.handler;

import com.jaksimsam1.authservice.application.command.service.AuthCommandService;
import com.jaksimsam1.authservice.common.model.enums.ErrorCode;
import com.jaksimsam1.authservice.common.model.response.ApiResponse;
import com.jaksimsam1.authservice.presentation.command.request.LoginRequest;
import com.jaksimsam1.authservice.presentation.command.request.TokenRefreshRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AuthCommandHandler {

    private final AuthCommandService authCommandService;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(authCommandService::login)
                .flatMap(response -> ServerResponse.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getAccessToken())
                        .header("Refresh-Token", "Bearer " + response.getRefreshToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ApiResponse.create()))
                .onErrorResume(e -> ServerResponse.status(ErrorCode.UNAUTHORIZED.getStatus())
                        .bodyValue(ApiResponse.error()))
                .log();
    }

    public Mono<ServerResponse> refresh(ServerRequest request) {
        return request.bodyToMono(TokenRefreshRequest.class)
                .flatMap(authCommandService::refresh)
                .flatMap(response -> ServerResponse.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getNewAccessToken())
                        .bodyValue(ApiResponse.create()))
                .onErrorResume(e -> ServerResponse.status(ErrorCode.SERVER_ERROR.getStatus())
                        .bodyValue(ApiResponse.error()))
                .log();
    }


}
