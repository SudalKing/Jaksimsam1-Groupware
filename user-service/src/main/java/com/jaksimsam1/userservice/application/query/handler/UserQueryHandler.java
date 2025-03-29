package com.jaksimsam1.userservice.application.query.handler;

import com.jaksimsam1.userservice.application.query.dto.UserDto;
import com.jaksimsam1.userservice.application.query.service.UserQueryService;
import com.jaksimsam1.userservice.common.model.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserQueryHandler {

    private final UserQueryService userQueryService;

    public Mono<ApiResponse<UserDto>> findUserById(UUID userId) {
        return userQueryService.findUserById(userId);
    }
}
