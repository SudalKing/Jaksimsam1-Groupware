package com.jaksimsam1.authservice.support;

import com.jaksimsam1.authservice.container.KafkaTestContainer;
import com.jaksimsam1.authservice.container.PostgresTestContainer;
import com.jaksimsam1.authservice.container.RedisTestContainer;
import com.jaksimsam1.authservice.infra.messaging.model.topic.KafkaTopics;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;


@EmbeddedKafka(partitions = 2, topics = {KafkaTopics.USER_CREATED_TOPIC})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public abstract class IntegrationTestContainer extends PostgresTestContainer
        implements RedisTestContainer, KafkaTestContainer {

    @Autowired
    private DatabaseClient databaseClient;

    @BeforeAll
    void initDatabase() {
        initializeTables(databaseClient);
    }

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        KafkaTestContainer.kafkaContainerProperties(registry);
        RedisTestContainer.redisContainerProperties(registry);
    }

}
