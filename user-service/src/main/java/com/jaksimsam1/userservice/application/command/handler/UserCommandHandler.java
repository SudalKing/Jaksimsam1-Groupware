package com.jaksimsam1.userservice.application.command.handler;

import com.jaksimsam1.commondto.model.response.ApiResponse;
import com.jaksimsam1.userservice.application.command.service.UserCommandService;
import com.jaksimsam1.userservice.presentation.command.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class UserCommandHandler {

    private final UserCommandService userCommandService;

    public Mono<ApiResponse<Void>> createUser(CreateUserRequest request) {
        return userCommandService.createUser(request)
                .thenReturn(ApiResponse.create());
    }
}
