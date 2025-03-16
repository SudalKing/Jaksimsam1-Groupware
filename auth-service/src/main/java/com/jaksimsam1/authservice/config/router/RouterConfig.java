package com.jaksimsam1.authservice.config.router;

import com.jaksimsam1.authservice.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class RouterConfig {

    private final AuthService authService;

    @Bean
    public RouterFunction<ServerResponse> loginRoutes() {
        return RouterFunctions.route()
                .POST("/auth/api/v1/login", accept(MediaType.APPLICATION_JSON), authService::login)
                .build();
    }
}
