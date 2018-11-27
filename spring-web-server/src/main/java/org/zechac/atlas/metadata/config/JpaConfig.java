package org.zechac.atlas.metadata.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.zechac.atlas.common.repo.BaseRepoImpl;

@EnableJpaRepositories(basePackages = {"org.zechac.atlas.rbac.repo", "org.zechac.atlas.demo.repo"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        repositoryBaseClass = BaseRepoImpl.class
)
@PropertySource(value = "classpath:jdbc-config.properties")
@Configuration
public class JpaConfig {
}
