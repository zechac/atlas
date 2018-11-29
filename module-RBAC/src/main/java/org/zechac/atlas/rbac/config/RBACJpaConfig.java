package org.zechac.atlas.rbac.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.zechac.atlas.common.repo.BaseRepoImpl;

@EnableJpaRepositories(basePackages = {"org.zechac.atlas.rbac.repo"},
        repositoryBaseClass = BaseRepoImpl.class
)
@Configuration
public class RBACJpaConfig {
}
