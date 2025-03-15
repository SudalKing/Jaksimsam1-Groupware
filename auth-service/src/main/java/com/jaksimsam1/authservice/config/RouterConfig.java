package com.jaksimsam1.authservice.config;

import com.jaksimsam1.authservice.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@RequiredArgsConstructor
@Configuration
public class RouterConfig {

    private final AuthService authService;
    private final RouterFilterConfig routerFilterConfig;

    @Bean
    public RouterFunction<ServerResponse> loginRoutes() {
        return RouterFunctions.route()
                .POST("/auth/api/v1/login", accept(MediaType.APPLICATION_JSON), authService::login)
//                .filter(routerFilterConfig::routerFilter)
                .build();
    }
}
