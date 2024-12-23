package com.jaksimsam1.userservice.aop;

import com.jaksimsam1.userservice.annotation.RedisCacheable;
import com.jaksimsam1.userservice.config.redis.RedisCacheUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Aspect
@RequiredArgsConstructor
@Component
public class ReactiveCacheAspect {
    private final RedisCacheUtil redisCacheUtil;

    @Around("@annotation(redisCacheable)")
    public Object cache(ProceedingJoinPoint joinPoint, RedisCacheable redisCacheable) throws Throwable {
        String key = redisCacheable.key();
        Duration ttl = Duration.ofSeconds(redisCacheable.ttl());
        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }

        if (result instanceof Mono) {
            return redisCacheUtil.cache(key, (Mono<?>) result, ttl);
        } else if (result instanceof Flux) {
            return redisCacheUtil.cache(key, (Flux<?>) result, ttl);
        } else {
            throw new IllegalStateException("");
        }
    }
}
