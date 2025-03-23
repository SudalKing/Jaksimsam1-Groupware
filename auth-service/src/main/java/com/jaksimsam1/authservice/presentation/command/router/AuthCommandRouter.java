package com.jaksimsam1.authservice.presentation.command.router;

import com.jaksimsam1.authservice.application.command.handler.AuthCommandHandler;
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
public class AuthCommandRouter {

    private final AuthCommandHandler authCommandHandler;

    @Bean
    public RouterFunction<ServerResponse> loginRoutes() {
        return RouterFunctions.route()
                .POST("/auth/api/v1/login", accept(MediaType.APPLICATION_JSON), authCommandHandler::login)
                .build();
    }
}
