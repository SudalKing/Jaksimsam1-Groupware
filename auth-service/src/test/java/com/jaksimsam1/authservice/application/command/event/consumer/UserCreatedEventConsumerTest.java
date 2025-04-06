package com.jaksimsam1.authservice.application.command.event.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaksimsam1.authservice.domain.auth.repository.AuthRepository;
import com.jaksimsam1.authservice.infra.messaging.model.topic.KafkaTopics;
import com.jaksimsam1.authservice.presentation.command.request.AuthCreateRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Testcontainers
@DirtiesContext
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 2, topics = {KafkaTopics.USER_CREATED_TOPIC})
@SpringBootTest
public class UserCreatedEventConsumerTest {

    private static final Logger log = LoggerFactory.getLogger(UserCreatedEventConsumerTest.class);
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:12-alpine"))
            .withDatabaseName("auth")
            .withUsername("user")
            .withPassword("password");

    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.2.1"));

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private DatabaseClient databaseClient;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        // R2DBC
        registry.add("spring.r2dbc.url", () ->
                "r2dbc:postgresql://" + postgres.getHost() + ":" + postgres.getMappedPort(5432) + "/" + postgres.getDatabaseName());
        registry.add("spring.r2dbc.username", postgres::getUsername);
        registry.add("spring.r2dbc.password", postgres::getPassword);

        // Kafka
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @BeforeEach
    void setUpSchema() {
        String ddl = """
            CREATE TABLE IF NOT EXISTS auth (
                auth_id UUID PRIMARY KEY,
                user_id UUID NOT NULL,
                email VARCHAR(100) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                status VARCHAR(20) NOT NULL,
                role VARCHAR(20) NOT NULL,
                last_login TIMESTAMP(6) NOT NULL,
                last_password_change TIMESTAMP(6) NOT NULL,
                last_status_modified TIMESTAMP(6) NOT NULL,
                created_at TIMESTAMP(6) NOT NULL
            );
        """;

        databaseClient.sql(ddl).then().block();
    }

    @BeforeEach
    void setup() {
        Map<String, Object> senderProps = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer",
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"
        );
        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(senderProps);
        kafkaTemplate = new KafkaTemplate<>(producerFactory);
    }

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