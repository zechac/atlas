package org.zechac.atlas.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
@Primary
public abstract class BaseJpaConfig {

    @Bean
    @Primary
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        return jpaVendorAdapter;
    }

    /**
     * 初始化jpa实体工厂
     *
     * @return
     */
    @Bean(name = "primaryEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(Environment environment, DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource);
        //获取需要检测的包
        String[] packages = environment.getRequiredProperty("jpa.entity.packages").split("[;,]");

        entityManagerFactory.setPackagesToScan(packages);

        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());

        entityManagerFactory.setPersistenceUnitName("jpaPersistenceUnit");
        entityManagerFactory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);

        //jpa属性
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        jpaProperties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql", "true"));
        jpaProperties.setProperty("hibernate.format_sql", environment.getProperty("hibernate.format_sql", "true"));
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto", "update"));
        jpaProperties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect"));
        if ("true".equals(environment.getProperty("hibernate.cache.use_second_level_cache", "false"))) {
            jpaProperties.setProperty("hibernate.cache.use_second_level_cachel", environment.getProperty("hibernate.cache.use_second_level_cache", "true"));
            jpaProperties.setProperty("hibernate.cache.use_query_cache", environment.getProperty("hibernate.cache.use_query_cache", "true"));
            jpaProperties.setProperty("hibernate.cache.region.factory_class", environment.getProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.redis.hibernate5.SingletonRedisRegionFactory"));
            jpaProperties.setProperty("hibernate.cache.region_prefix", environment.getProperty("hibernate.cache.region_prefix", "hibernate"));
        }
        configJpaProperties(jpaProperties);
        entityManagerFactory.setJpaProperties(jpaProperties);

        return entityManagerFactory;
    }

    public void configJpaProperties(Properties jpaProperties) {

    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }
}
