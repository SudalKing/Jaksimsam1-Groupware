package com.jaksimsam1.userservice.user.service;

import com.jaksimsam1.commondto.common.kafka.KafkaTopics;
import com.jaksimsam1.commondto.model.enums.ErrorCode;
import com.jaksimsam1.commondto.model.response.ApiResponse;
import com.jaksimsam1.userservice.annotation.FluxCacheable;
import com.jaksimsam1.userservice.annotation.MonoCacheable;
import com.jaksimsam1.userservice.event.model.UserCreateEvent;
import com.jaksimsam1.userservice.event.producer.KafkaProducer;
import com.jaksimsam1.userservice.user.dto.UserDto;
import com.jaksimsam1.userservice.user.entity.Users;
import com.jaksimsam1.userservice.user.exception.UserNotFoundException;
import com.jaksimsam1.userservice.user.model.enums.Gender;
import com.jaksimsam1.userservice.user.model.request.UserCreateRequest;
import com.jaksimsam1.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final KafkaProducer kafkaProducer;

    @MonoCacheable(key = "user:#{#userId}", ttl = 600)
    public Mono<UserDto> findUserById(UUID userId) {
        return Mono.fromCallable(() -> userRepository.findByUserId(userId))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Mono::justOrEmpty)
                .map(UserDto::of)
                .switchIfEmpty(Mono.error(new UserNotFoundException("No Such User: " + userId, ErrorCode.ENTITY_NOT_FOUND)));
    }

    @FluxCacheable(key = "user:list", ttl = 600)
    public Flux<UserDto> findAll() {
        return Flux.defer(() -> Flux.fromIterable(userRepository.findAll()))
                .switchIfEmpty(Flux.error(new UserNotFoundException("No Such Users", ErrorCode.ENTITY_NOT_FOUND)))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(user -> Flux.just(UserDto.of(user)));
    }

    public Mono<ApiResponse<Void>> createUser(UserCreateRequest request) {
        Users user = Users.builder()
                .username(request.getUsername())
                .tel(request.getTel())
                .gender(Gender.fromValue(request.getGender()))
                .useYn("Y")
                .build();
        // 1. User 저장
        // 2. USER_CREATED_TOPIC 메세지 발행
        return Mono.fromCallable(() -> userRepository.save(user))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(savedUser -> {
                    UserCreateEvent event = new UserCreateEvent(savedUser.getUserId(), request.getEmail(), request.getPassword());
                    kafkaProducer.sendMessage(KafkaTopics.USER_CREATED_TOPIC, savedUser.getUserId().toString(), event);
                })
                .thenReturn(ApiResponse.create());
    }
}
