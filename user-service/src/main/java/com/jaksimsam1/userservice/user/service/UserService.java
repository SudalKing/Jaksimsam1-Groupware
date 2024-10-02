package com.jaksimsam1.userservice.user.service;

import com.jaksimsam1.userservice.user.dto.UserDto;
import com.jaksimsam1.userservice.user.entity.User;
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
    private final RedisTemplate redisTemplate;

    public Mono<UserDto> findUserById(String userId) {
        return Mono.fromCallable(() -> userRepository.findByUserId(userId))
                .subscribeOn(Schedulers.boundedElastic())
                .map(User::toUserDto);
    }

    public Flux<User> findAll() {
        return Flux.defer(() -> Flux.fromIterable(userRepository.findAll()))
                .subscribeOn(Schedulers.boundedElastic());
    }
}