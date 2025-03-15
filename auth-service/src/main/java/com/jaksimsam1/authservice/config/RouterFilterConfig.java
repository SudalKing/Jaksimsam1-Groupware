package com.jaksimsam1.authservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Configuration
public class RouterFilterConfig {

    public Mono<ServerResponse> routerFilter(ServerRequest request, HandlerFunction<ServerResponse> handlerFunction) {
        return request.exchange().getRequest().getBody()
                .collectList()
                .cache()
                .doOnNext(body -> requestLogging(request, body))
                .then(handlerFunction.handle(request))
                .doOnNext(response -> responseLogging(request, response));
    }

    private void requestLogging(ServerRequest request, List<DataBuffer> body) {
        log.info("Request: {} {} | {}", request.method(), request.path(), body);
    }

    private void responseLogging(ServerRequest request, ServerResponse response) {
        log.info("Response: {} {} | {}", request.method(), request.path(), ((EntityResponse<?>) response).entity());
    }
}
