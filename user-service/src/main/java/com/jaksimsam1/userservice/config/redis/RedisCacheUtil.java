package com.jaksimsam1.userservice.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisCacheUtil {
    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    public <T> Mono<T> cache(String key, Mono<T> value, Duration ttl) {
        return reactiveRedisTemplate.opsForValue()
                .get(key)
                .cast((Class<T>) value.getClass())
                .switchIfEmpty(
                        value.flatMap(result ->
                            reactiveRedisTemplate.opsForValue()
                                    .set(key, value, ttl)
                                    .thenReturn(result)
                        )
                );
    }

    public <T> Flux<T> cache(String key, Flux<T> value, Duration ttl) {
        return reactiveRedisTemplate.opsForList()
                .size(key)
                .flatMapMany(size -> {
                    if (size > 0) {
                        return reactiveRedisTemplate.opsForList()
                                .range(key, 0, size - 1)
                                .cast((Class<T>) value.getClass());
                    } else {
                        return value.collectList()
                                .flatMapMany(list -> reactiveRedisTemplate.opsForList()
                                        .rightPushAll(key, list)
                                        .then(reactiveRedisTemplate.expire(key, ttl))
                                        .thenMany(Flux.fromIterable(list))
                                );
                    }
                });
    }
}
