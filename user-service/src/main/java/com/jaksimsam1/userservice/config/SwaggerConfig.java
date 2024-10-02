package com.jaksimsam1.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("ALL")
                .pathsToExclude("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("USER")
                .packagesToScan("com.jaksimsam1.userservice")
                .pathsToMatch("/v1/api/user/**")
                .build();
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(
                new Info()
                        .title("User Service")
                        .description("작심삼일 그룹웨어 User Domain")
                        .version("v0.1")
        );
    }
}
