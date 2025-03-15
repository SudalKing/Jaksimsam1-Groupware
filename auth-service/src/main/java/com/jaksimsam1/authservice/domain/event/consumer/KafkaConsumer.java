package com.jaksimsam1.authservice.domain.event.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaksimsam1.authservice.domain.auth.model.request.AuthCreateRequest;
import com.jaksimsam1.authservice.domain.event.handler.UserCreatedEventHandler;
import com.jaksimsam1.commondto.common.kafka.KafkaConsumerGroup;
import com.jaksimsam1.commondto.common.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumer {

    private final ObjectMapper objectMapper;

    private final UserCreatedEventHandler userCreatedEventHandler;

    @KafkaListener(topics = KafkaTopics.USER_CREATED_TOPIC, groupId = KafkaConsumerGroup.USER_CREATED_GROUP, containerFactory = "kafkaListenerContainerFactory")
    public void consume(String message) {
        try {
            AuthCreateRequest request = objectMapper.readValue(message, AuthCreateRequest.class);
            log.info("[Kafka] Consume request {}", request);
            userCreatedEventHandler.handle(request);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize message: {}", message, e);
        }

    }
}
