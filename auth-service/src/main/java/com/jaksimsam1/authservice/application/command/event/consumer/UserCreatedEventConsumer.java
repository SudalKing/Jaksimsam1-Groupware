package com.jaksimsam1.authservice.application.command.event.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaksimsam1.authservice.application.command.service.AuthCommandService;
import com.jaksimsam1.authservice.common.model.response.ApiResponse;
import com.jaksimsam1.authservice.infra.messaging.model.topic.KafkaConsumerGroup;
import com.jaksimsam1.authservice.infra.messaging.model.topic.KafkaTopics;
import com.jaksimsam1.authservice.presentation.command.request.AuthCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserCreatedEventConsumer {

    private final ObjectMapper objectMapper;

    private final AuthCommandService authCommandService;

    @KafkaListener(topics = KafkaTopics.USER_CREATED_TOPIC, groupId = KafkaConsumerGroup.USER_CREATED_GROUP, containerFactory = "kafkaListenerContainerFactory")
    public void consume(String message) {
        try {
            AuthCreateRequest request = objectMapper.readValue(message, AuthCreateRequest.class);

            log.info("[Auth] Consume Event Request {}", request);
            authCommandService.createAuth(request)
                    .doOnSuccess(response -> log.info("[Auth] Auth Created {}", response))
                    .thenReturn(ApiResponse.create())
                    .subscribe();
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize message: {}", message, e);
        }

    }
}
