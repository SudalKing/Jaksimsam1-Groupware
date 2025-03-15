package com.jaksimsam1.userservice.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class RedisCacheUtil {
    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    public <T> Mono<T> getCache(String key, Class<T> clazz) {
        return reactiveRedisTemplate.opsForValue()
                .get(key)
                .cast(clazz);
    }

    public <T> Mono<T> setCache(String key, T value, Duration ttl) {
        return reactiveRedisTemplate.opsForValue()
                .set(key, value, ttl)
                .thenReturn(value);
    }

    public Mono<Boolean> deleteCache(String key) {
        return reactiveRedisTemplate.opsForValue()
                .delete(key);
    }

    public <T> Flux<T> getCacheList(String key, Class<T> clazz) {
        return reactiveRedisTemplate.opsForList()
                .range(key, 0, -1)
                .cast(clazz);
    }

    public <T> Flux<T> setCacheList(String key, Flux<T> values, Duration ttl) {
        return values.collectList()
                .flatMapMany(list ->
                        reactiveRedisTemplate.opsForList()
                                .rightPushAll(key, list)
                                .then(reactiveRedisTemplate.expire(key, ttl))
                                .thenMany(Flux.fromIterable(list))
                );
    }
}
