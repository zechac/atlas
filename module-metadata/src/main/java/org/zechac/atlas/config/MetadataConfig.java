package org.zechac.atlas.config;

import org.zechac.atlas.metadata.jpa.JpaReflector;
import org.zechac.atlas.metadata.mysql.Reflector;
import org.zechac.atlas.metadata.mysql.Reflector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManagerFactory;

@Configuration
public class MetadataConfig {

    @Bean
    public Reflector reflector(JdbcTemplate jdbcTemplate) {
        return new Reflector(jdbcTemplate);
    }

    @Bean
    @ConditionalOnBean(EntityManagerFactory.class)
    public JpaReflector jpaReflector(EntityManagerFactory entityManagerFactory) {
        return new JpaReflector(entityManagerFactory);
    }
}
