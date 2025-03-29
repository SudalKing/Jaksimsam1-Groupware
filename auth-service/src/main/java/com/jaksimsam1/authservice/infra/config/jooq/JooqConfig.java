package com.jaksimsam1.authservice.infra.config.jooq;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.ExecuteWithoutWhere;
import org.jooq.impl.DSL;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
public class JooqConfig {

    @Bean
    public DSLContext dslContext(DatabaseClient databaseClient) {
        return DSL.using(databaseClient.getConnectionFactory(), SQLDialect.POSTGRES);
    }

    @Bean
    public DefaultConfigurationCustomizer jooqDefaultConfigurationCustomizer() {
        return configuration -> configuration.settings()
                .withExecuteDeleteWithoutWhere(ExecuteWithoutWhere.THROW)
                .withExecuteUpdateWithoutWhere(ExecuteWithoutWhere.THROW)
                .withRenderSchema(false)
                .withRenderFormatted(true)
                .withBatchSize(100)
                .withQueryTimeout(60);
    }
}
