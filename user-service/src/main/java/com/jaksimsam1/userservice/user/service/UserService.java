package com.jaksimsam1.userservice.user.service;

import com.jaksimsam1.commondto.common.exception.ErrorCode;
import com.jaksimsam1.userservice.user.dto.UserDto;
import com.jaksimsam1.userservice.user.exception.UserNotFoundException;
import com.jaksimsam1.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ReactiveRedisTemplate<String, UserDto> reactiveRedisTemplate;

    public Mono<UserDto> findUserById(String userId) {
        return reactiveRedisTemplate.opsForValue().get(userId)
                .switchIfEmpty(
                        Mono.defer(() -> Mono.justOrEmpty(userRepository.findByUserId(userId))
                                .subscribeOn(Schedulers.boundedElastic())
                                .switchIfEmpty(Mono.error(new UserNotFoundException("User Not Found Exception", ErrorCode.ENTITY_NOT_FOUND)))
                                .flatMap(user -> {
                                    UserDto userDto = user.toUserDto();
                                    return reactiveRedisTemplate.opsForValue().set(userId, userDto)
                                            .thenReturn(userDto);
                                })
                        )
                );
    }

    public Flux<UserDto> findAll() {
        return Flux.defer(() -> Flux.fromIterable(userRepository.findAll()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(user -> Flux.just(user.toUserDto()));
    }
}
