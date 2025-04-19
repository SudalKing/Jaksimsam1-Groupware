package com.jaksimsam1.authservice.config;

import com.jaksimsam1.authservice.container.KafkaTestContainer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@TestConfiguration
public class KafkaTestConfig {

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
            Map<String, Object> senderProps = Map.of(
                    ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaTestContainer.kafkaContainer.getBootstrapServers(),
                    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer",
                    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer"
            );
            ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(senderProps);
            return new KafkaTemplate<>(producerFactory);
    }
}
