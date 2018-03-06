package com.hongdee.atlas.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public abstract class JdbcConfig implements EnvironmentAware {

    @Autowired
    protected Environment environment;

    public void setEnvironment(Environment environment){
        this.environment=environment;
    }

    /**
     * 获取数据库连接池，支持单源和多源数据库连接
     */
    @Bean(destroyMethod="close",initMethod = "init")
    @Primary
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
