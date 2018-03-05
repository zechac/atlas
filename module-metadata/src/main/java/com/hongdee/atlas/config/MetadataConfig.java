package com.hongdee.atlas.config;

import com.hongdee.atlas.common.sql.SqlTemplate;
import com.hongdee.atlas.metadata.mysql.Reflector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MetadataConfig {

    @Autowired
    private SqlTemplate sqlTemplate;

    @Bean
    public Reflector reflector(){
        return new Reflector(sqlTemplate);
    }
}
