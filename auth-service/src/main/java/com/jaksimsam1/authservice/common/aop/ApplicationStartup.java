package com.jaksimsam1.authservice.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationStartup {

    @Value("${spring.config.activate.on-profile}")
    private String profile;

    @EventListener(ApplicationReadyEvent.class)
    public void startupLog() {
        log.info("Start Profile: {}", profile);
    }
}
