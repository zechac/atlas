package com.hongdee.atlas.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import com.hongdee.atlas.common.convert.StringToDateConverter;
import com.hongdee.atlas.common.jpa.query.MapQueryBuilder;
import com.hongdee.atlas.common.jpa.query.StringMapQueryBuilder;

import java.util.Date;

/**
 * Created by hua on 2016/10/14.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BaseAppConfig {
    @Autowired
    Environment environment;

    @Bean
    public ConversionService conversionService(){
        DefaultConversionService defaultConversionService=new DefaultConversionService();
        defaultConversionService.removeConvertible(String.class, Date.class);
        defaultConversionService.addConverter(String.class,Date.class,new StringToDateConverter("yyyy-MM-dd HH:mm:ss"));
        configConversionService(defaultConversionService);
        return defaultConversionService;
    }

    public void configConversionService(DefaultConversionService defaultConversionService){

    }

    @Bean
    public MapQueryBuilder mapQueryBuilder(ConversionService conversionService){
        MapQueryBuilder cus=createMapQueryBuilder();
        if(cus!=null){
            return cus;
        }
        StringMapQueryBuilder stringMapQueryBuilder=new StringMapQueryBuilder();
        stringMapQueryBuilder.setConversionService(conversionService);
        return stringMapQueryBuilder;
    }

    /**
     * 自定义MapQueryBuilder
     * @return
     */
    public MapQueryBuilder createMapQueryBuilder(){
        return null;
    }

}
