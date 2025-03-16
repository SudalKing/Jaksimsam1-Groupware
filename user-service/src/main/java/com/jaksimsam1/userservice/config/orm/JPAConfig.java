package com.jaksimsam1.userservice.config.orm;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.jaksimsam1.userservice.user.repository")
@EnableJpaAuditing
@Configuration
public class JPAConfig {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * QueryDSL query Factory setting
     * @return JPAQueryFactory
     */
    @Bean
    public JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
