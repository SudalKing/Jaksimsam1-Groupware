package com.jaksimsam1.authservice.application.command.event.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaksimsam1.authservice.domain.auth.repository.AuthRepository;
import com.jaksimsam1.authservice.infra.messaging.model.topic.KafkaTopics;
import com.jaksimsam1.authservice.presentation.command.request.AuthCreateRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.UUID;

@SpringBootTest
public class UserCreatedEventConsumerTest {

    private static final Logger log = LoggerFactory.getLogger(UserCreatedEventConsumerTest.class);

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthRepository authRepository;

    @Test
    void consume() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        String email = "test@test.com";
        String password = "password";

        AuthCreateRequest request = new AuthCreateRequest(userId, email, password);
        String jsonMessage = objectMapper.writeValueAsString(request);

        log.info("Request: {}", request);
        log.info("jsonMessage: {}", jsonMessage);

        // When
        kafkaTemplate.send(KafkaTopics.USER_CREATED_TOPIC, jsonMessage);

        // Then
        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> {
                    StepVerifier.create(authRepository.findByEmail(email))
                            .expectNextMatches(auth -> auth.getUserId().equals(userId))
                            .verifyComplete();
                });
    }
}