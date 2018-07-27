package org.zechac.atlas.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by hua on 2016/10/14.
 * jpa审计信息处理配置
 */
@Configuration
@EnableJpaAuditing
public abstract class AuditJpaConfig extends BaseJpaConfig {

    @Bean
    public abstract AuditorAware<String> auditorProvider();
}
