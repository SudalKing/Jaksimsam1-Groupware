package com.jaksimsam1.authservice.container;

import com.jaksimsam1.authservice.infra.messaging.model.topic.KafkaTopics;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@EmbeddedKafka(partitions = 2, topics = {KafkaTopics.USER_CREATED_TOPIC})
public interface KafkaTestContainer {

    String KAFKA_VERSION = "confluentinc/cp-kafka:7.2.1";

    KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse(KAFKA_VERSION));

    static void kafkaContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        kafkaContainer.start();
        System.out.println("▶\uFE0F Kafka container started: " + kafkaContainer.getBootstrapServers());
    }
}
