package com.jaksimsam1.userservice.application.command.service;

import com.jaksimsam1.userservice.infra.messaging.topic.KafkaTopics;
import com.jaksimsam1.userservice.infra.persistence.entity.Users;
import com.jaksimsam1.userservice.domain.user.model.enums.Gender;
import com.jaksimsam1.userservice.presentation.command.request.CreateUserRequest;
import com.jaksimsam1.userservice.domain.user.repository.UserRepository;
import com.jaksimsam1.userservice.infra.messaging.model.UserCreateEvent;
import com.jaksimsam1.userservice.infra.messaging.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Service
public class UserCommandService {

    private final UserRepository userRepository;
    private final KafkaProducer kafkaProducer;

    public Mono<Users> createUser(CreateUserRequest request) {
        Users user = Users.builder()
                .username(request.getUsername())
                .tel(request.getTel())
                .gender(Gender.fromValue(request.getGender()))
                .useYn("Y")
                .build();

        return Mono.fromCallable(() -> userRepository.save(user))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(savedUser -> {
                    UserCreateEvent event = new UserCreateEvent(savedUser.getUserId(), request.getEmail(), request.getPassword());
                    kafkaProducer.sendMessage(KafkaTopics.USER_CREATED_TOPIC, savedUser.getUserId().toString(), event);
                });
    }
}
