package com.jaksimsam1.authservice.domain.auth.controller;

import com.jaksimsam1.authservice.domain.auth.model.request.RefreshTokenRequest;
import com.jaksimsam1.authservice.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/api/v1")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/refresh")
    public Mono<ServerResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refresh(refreshTokenRequest);
    }

    @PostMapping("/{email}")
    public Mono<Void> logout(@PathVariable(value = "email") String email) {
        return authService.logout(email);
    }
}
