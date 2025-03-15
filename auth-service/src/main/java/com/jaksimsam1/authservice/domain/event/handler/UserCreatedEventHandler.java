package com.jaksimsam1.authservice.domain.event.handler;

import com.jaksimsam1.authservice.domain.auth.model.request.AuthCreateRequest;
import com.jaksimsam1.authservice.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserCreatedEventHandler {

    private final AuthService authService;

    public void handle(AuthCreateRequest request) {
        log.info("[Handler] Handle request {}", request);

        authService.createAuth(request);
    }
}
