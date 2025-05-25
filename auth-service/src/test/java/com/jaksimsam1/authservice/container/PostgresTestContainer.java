package com.jaksimsam1.authservice.container;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class PostgresTestContainer {

    private final static String POSTGRESQL_VERSION = "postgres:12-alpine";
    private final static String POSTGRESQL_USER = "user";
    private final static String POSTGRESQL_PASSWORD = "password";
    private final static String POSTGRESQL_DATABASE = "auth";

    private static final PostgreSQLContainer<?> postgresContainer;

    static {
        postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRESQL_VERSION))
                .withDatabaseName(POSTGRESQL_DATABASE)
                .withUsername(POSTGRESQL_USER)
                .withPassword(POSTGRESQL_PASSWORD);
        postgresContainer.start();

        System.out.println("▶\uFE0F PostgreSQL container started: " + postgresContainer.getJdbcUrl());
    }

    @DynamicPropertySource
    static void postgresContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () ->
                "r2dbc:postgresql://" + postgresContainer.getHost() + ":" + postgresContainer.getMappedPort(5432) + "/" + postgresContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", postgresContainer::getUsername);
        registry.add("spring.r2dbc.password", postgresContainer::getPassword);
    }

    public static void initializeTables(DatabaseClient databaseClient) {
        String ddl = """
            CREATE TABLE IF NOT EXISTS auth (
                auth_id UUID PRIMARY KEY,
                user_id UUID NOT NULL,
                email VARCHAR(100) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                status VARCHAR(20) NOT NULL,
                role VARCHAR(20) NOT NULL,
                last_login TIMESTAMP(6) NOT NULL,
                last_password_change TIMESTAMP(6) NOT NULL,
                last_status_modified TIMESTAMP(6) NOT NULL,
                created_at TIMESTAMP(6) NOT NULL
            );
        """;

        databaseClient.sql(ddl).then().block();
    }
}
