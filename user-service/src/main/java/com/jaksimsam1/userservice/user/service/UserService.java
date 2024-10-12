package com.jaksimsam1.userservice.user.service;

import com.jaksimsam1.commondto.common.exception.ErrorCode;
import com.jaksimsam1.userservice.user.dto.UserDto;
import com.jaksimsam1.userservice.user.exception.UserNotFoundException;
import com.jaksimsam1.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RedisTemplate<String, UserDto> redisTemplate;

    public Mono<UserDto> findUserById(String userId) {
        return Mono.defer(() -> Mono.justOrEmpty(userRepository.findByUserId(userId)))
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new UserNotFoundException("User Not Found Error", ErrorCode.ENTITY_NOT_FOUND)))
                .flatMap(user -> {
                    UserDto userDto = user.toUserDto();
                    return Mono.fromCallable(() -> {
                        redisTemplate.opsForValue().set(userId, userDto);
                        return userDto;
                    });
                });
    }

    public Flux<UserDto> findAll() {
        return Flux.defer(() -> Flux.fromIterable(userRepository.findAll()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(user -> Flux.just(user.toUserDto()));
    }
}
