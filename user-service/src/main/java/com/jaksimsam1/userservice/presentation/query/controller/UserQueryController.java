package com.jaksimsam1.userservice.presentation.query.controller;

import com.jaksimsam1.commondto.model.response.ApiResponse;
import com.jaksimsam1.userservice.application.query.dto.UserDto;
import com.jaksimsam1.userservice.application.query.service.UserQueryService;
import com.jaksimsam1.userservice.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/user/api/v1")
@RestController
public class UserQueryController {

    private final UserQueryService userQueryService;

    @GetMapping("/{userId}")
    public Mono<ApiResponse<UserDto>> findUserById(@PathVariable("userId") UUID userId) throws UserNotFoundException {
        return userQueryService.findUserById(userId);
    }

    @GetMapping("/all")
    public Flux<ApiResponse<UserDto>> findUsers() {
        return userQueryService.findAll();
    }
}
