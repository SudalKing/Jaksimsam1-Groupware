package com.jaksimsam1.userservice.aop;

import com.jaksimsam1.userservice.annotation.FluxCacheable;
import com.jaksimsam1.userservice.config.redis.RedisCacheUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Aspect
@RequiredArgsConstructor
@Component
public class FluxCacheAspect {

    private final RedisCacheUtil redisCacheUtil;

    @Around("@annotation(fluxCacheable)")
    public Object cache(ProceedingJoinPoint joinPoint, FluxCacheable fluxCacheable) throws Throwable {
        Object result = joinPoint.proceed();
        if (!(result instanceof Flux)) throw new IllegalArgumentException("Not Supported, Only Flux");

        String key = fluxCacheable.key();
        Duration ttl = Duration.ofSeconds(fluxCacheable.ttl());

        return redisCacheUtil.getCacheList(key, Object.class)
                .collectList()
                .flatMapMany(cachedList -> {
                    if (!cachedList.isEmpty()) {
                        return Flux.fromIterable(cachedList);
                    }
                    return ((Flux<?>) result)
                            .collectList()
                            .flatMapMany(newList -> redisCacheUtil.deleteCache(key)
                                    .thenMany(redisCacheUtil.setCacheList(key, Flux.fromIterable(newList), ttl)));
                });
    }
}
