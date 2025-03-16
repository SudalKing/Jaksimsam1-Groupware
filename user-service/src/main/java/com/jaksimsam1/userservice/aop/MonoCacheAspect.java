package com.jaksimsam1.userservice.aop;

import com.jaksimsam1.userservice.annotation.MonoCacheable;
import com.jaksimsam1.userservice.config.redis.RedisCacheUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Aspect
@RequiredArgsConstructor
@Component
public class MonoCacheAspect {

    private final RedisCacheUtil redisCacheUtil;

    @Around("@annotation(monoCacheable)")
    public Object cache(ProceedingJoinPoint joinPoint, MonoCacheable monoCacheable) throws Throwable {
        Object result = joinPoint.proceed();
        if (!(result instanceof Mono)) throw new IllegalArgumentException("Not Supported, Only Mono");

        String key = monoCacheable.key();
        Duration ttl = Duration.ofSeconds(monoCacheable.ttl());

        return redisCacheUtil.getCache(key, Object.class)
                .flatMap(Mono::just)
                .switchIfEmpty((Mono<?>) result)
                .flatMap(value -> redisCacheUtil.setCache(key, value, ttl)
                        .thenReturn(value));
    }
}
