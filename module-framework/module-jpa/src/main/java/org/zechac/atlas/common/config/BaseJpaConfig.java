package org.zechac.atlas.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.zechac.atlas.common.jpa.query.MapQueryBuilder;
import org.zechac.atlas.common.jpa.query.StringMapQueryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by hua on 2016/10/14.
 */
@Configuration
@EnableTransactionManagement
@ConditionalOnBean(DataSource.class)
@Slf4j
public class BaseJpaConfig {
    /**
     * 基于前台参数的查询构建器
     * {@link MapQueryBuilder}
     *
     * @param conversionService
     * @return
     */
    @Bean
    public MapQueryBuilder mapQueryBuilder(@Nullable ConversionService conversionService) {
        MapQueryBuilder cus = createMapQueryBuilder();
        if (cus != null) {
            return cus;
        }
        StringMapQueryBuilder stringMapQueryBuilder = new StringMapQueryBuilder();
        stringMapQueryBuilder.setConversionService(conversionService);
        return stringMapQueryBuilder;
    }

    /**
     * 自定义MapQueryBuilder
     *
     * @return
     */
    public MapQueryBuilder createMapQueryBuilder() {
        return null;
    }
}
