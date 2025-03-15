package com.jaksimsam1.userservice.event.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public <T> void sendMessage(String topic, String key, T data) {
        log.info("Sending Data to Kafka topic: {}, data: {}", topic, data);

        kafkaTemplate.send(topic, key, data);
    }

}
