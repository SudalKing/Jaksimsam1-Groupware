package com.jaksimsam1.authservice.container;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public interface RedisTestContainer {

    String REDIS_VERSION = "redis:7.0";
    int REDIS_PORT = 6379;

    GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse(REDIS_VERSION));

    static void redisContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(REDIS_PORT));
        redisContainer.start();
        System.out.println("▶\uFE0F Redis container started on port: " + redisContainer.getMappedPort(REDIS_PORT));
    }

}
