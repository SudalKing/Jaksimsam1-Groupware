package com.jaksimsam1.userservice.presentation.command.controller;

import com.jaksimsam1.commondto.model.response.ApiResponse;
import com.jaksimsam1.userservice.application.command.handler.UserCommandHandler;
import com.jaksimsam1.userservice.presentation.command.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/user/api/v1")
@RestController
public class UserCommandController {

    private final UserCommandHandler userCommandHandler;

    @PostMapping("/register")
    public Mono<ApiResponse<Void>> register(@RequestBody CreateUserRequest request) {
        return userCommandHandler.createUser(request);
    }
}
