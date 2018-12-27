package org.zechac.atlas.rbac.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.zechac.atlas.common.repo.BaseRepoImpl;

@EnableJpaRepositories(basePackages = {"org.zechac.atlas.rbac.repo"},
        repositoryBaseClass = BaseRepoImpl.class
)
@Configuration
@EntityScan("org.zechac.atlas.rbac.entity")
public class RBACJpaConfig {
}
