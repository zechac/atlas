package org.zechac.atlas.metadata.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.zechac.atlas.common.repo.BaseRepoImpl;
import org.zechac.atlas.metadata.jpa.JpaReflector;
import org.zechac.atlas.metadata.mysql.Reflector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManagerFactory;

@EnableJpaRepositories(basePackages = {"org.zechac.atlas.metadata.repo"},
        repositoryBaseClass = BaseRepoImpl.class
)
@Configuration
@EntityScan("org.zechac.atlas.metadata.entity")
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
