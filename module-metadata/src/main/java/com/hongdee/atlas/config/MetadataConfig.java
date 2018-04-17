package com.hongdee.atlas.config;

import com.hongdee.atlas.metadata.jpa.JpaReflector;
import com.hongdee.atlas.metadata.mysql.Reflector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManagerFactory;

@Configuration
public class MetadataConfig {

    @Bean
    public Reflector reflector(JdbcTemplate jdbcTemplate){
        return new Reflector(jdbcTemplate);
    }

    @Bean
    @ConditionalOnBean(EntityManagerFactory.class)
    public JpaReflector jpaReflector(EntityManagerFactory entityManagerFactory){
        return new JpaReflector(entityManagerFactory);
    }
}
