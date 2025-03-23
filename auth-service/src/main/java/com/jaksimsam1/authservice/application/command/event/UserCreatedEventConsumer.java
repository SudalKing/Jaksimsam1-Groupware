package com.jaksimsam1.authservice.application.command.event;

import com.jaksimsam1.authservice.application.command.service.AuthCommandService;
import com.jaksimsam1.authservice.presentation.command.request.AuthCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserCreatedEventConsumer {

    private final AuthCommandService authCommandService;

    public void handle(AuthCreateRequest request) {
        log.info("[Handler] Handle request {}", request);
        authCommandService.createAuth(request);
    }
}
