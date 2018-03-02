package com.hongdee.atlas.config;


import com.hongdee.atlas.common.config.BaseJpaConfig;
import com.hongdee.atlas.common.repo.BaseRepoImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.hongdee.atlas.repo"},
        entityManagerFactoryRef="primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager",
        repositoryBaseClass = BaseRepoImpl.class
)
@PropertySource(value = "classpath:jdbc-config.properties")
@Configuration
public class JpaConfig extends BaseJpaConfig{
}
