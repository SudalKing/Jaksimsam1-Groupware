package com.jaksimsam1.userservice.user.service;

import com.jaksimsam1.commondto.model.enums.ErrorCode;
import com.jaksimsam1.userservice.annotation.RedisCacheable;
import com.jaksimsam1.userservice.user.dto.UserDto;
import com.jaksimsam1.userservice.user.exception.UserNotFoundException;
import com.jaksimsam1.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @RedisCacheable(key = "user:#{#userId}", ttl = 60 * 10)
    public Mono<UserDto> findUserById(String userId) {
        return Mono.defer(() -> Mono.justOrEmpty(userRepository.findByUserId(userId))
                .switchIfEmpty(Mono.error(new UserNotFoundException("No Such User: " + userId, ErrorCode.ENTITY_NOT_FOUND))))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(user -> Mono.just(UserDto.of(user)));
    }

    @RedisCacheable(key = "allUsers")
    public Flux<UserDto> findAll() {
        return Flux.defer(() -> Flux.fromIterable(userRepository.findAll()))
                .switchIfEmpty(Flux.error(new UserNotFoundException("No Such Users", ErrorCode.ENTITY_NOT_FOUND)))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(user -> Flux.just(UserDto.of(user)));
    }


    public Flux<UserDto> findAllUsersInUse() {
        return Flux.defer(() -> Flux.fromIterable(userRepository.findAllUsersInUse())
                .switchIfEmpty(Flux.error(new UserNotFoundException("No Such Users In Use", ErrorCode.ENTITY_NOT_FOUND))))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(users -> Flux.just(UserDto.of(users)));
    }
}
