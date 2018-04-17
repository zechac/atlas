package com.hongdee.atlas.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.hongdee.atlas.common.jdbc.template.CommonQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class JdbcConfig implements EnvironmentAware {

    @Autowired
    protected Environment environment;

    @Override
    public void setEnvironment(Environment environment){
        this.environment=environment;
    }

    @Bean
    @ConditionalOnClass(JdbcTemplate.class)
    public JdbcTemplate sqlTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @ConditionalOnBean(JdbcTemplate.class)
    public CommonQuery commonQuery(JdbcTemplate jdbcTemplate){
        CommonQuery commonQuery=new CommonQuery();
        commonQuery.setJdbcTemplate(jdbcTemplate);
        return commonQuery;
    }

    /**
     * 获取数据库连接池，支持单源和多源数据库连接
     */
    @Bean(destroyMethod="close",initMethod = "init")
    @Primary
    @ConditionalOnMissingBean
    protected DataSource dataSource() {
        log.info("==========init db connection pool===============");
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.driver"));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        dataSource.setValidationQuery(environment.getProperty("jdbc.validationQuery"));
        try{
            if(Boolean.valueOf(environment.getProperty("druid.enableDataSourceMonitor","true"))){
                List arr=new ArrayList();
                dataSource.setProxyFilters(arr);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return dataSource;
    }

}
