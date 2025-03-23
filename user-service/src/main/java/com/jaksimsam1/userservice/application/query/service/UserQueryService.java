package com.jaksimsam1.userservice.application.query.service;

import com.jaksimsam1.commondto.model.enums.ErrorCode;
import com.jaksimsam1.commondto.model.response.ApiResponse;
import com.jaksimsam1.userservice.application.query.dto.UserDto;
import com.jaksimsam1.userservice.common.annotation.FluxCacheable;
import com.jaksimsam1.userservice.common.annotation.MonoCacheable;
import com.jaksimsam1.userservice.common.exception.UserNotFoundException;
import com.jaksimsam1.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserQueryService {

    private final UserRepository userRepository;

    @MonoCacheable(key = "user:#{#userId}", ttl = 600)
    public Mono<ApiResponse<UserDto>> findUserById(UUID userId) {
        return Mono.fromCallable(() -> userRepository.findByUserId(userId))
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new UserNotFoundException("No Search User: " + userId, ErrorCode.ENTITY_NOT_FOUND)))
                .map(UserDto::of)
                .flatMap(userDto ->  Mono.just(ApiResponse.create(userDto)));
    }

    @FluxCacheable(key = "user:list", ttl = 600)
    public Flux<ApiResponse<UserDto>> findAll() {
        return Flux.defer(() -> Flux.fromIterable(userRepository.findAll()))
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Flux.error(new UserNotFoundException("No Search Users", ErrorCode.ENTITY_NOT_FOUND)))
                .map(UserDto::of)
                .flatMap(userDto -> Flux.just(ApiResponse.create(userDto)));
    }
}
