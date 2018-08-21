package org.zechac.atlas.common.config;

import org.zechac.atlas.common.convert.StringToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.Date;

/**
 * Created by hua on 2016/10/14.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BaseAppConfig {

    /**
     * 类型转换器配置
     *
     * @return
     */
    @Bean
    public ConversionService conversionService() {
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        defaultConversionService.removeConvertible(String.class, Date.class);
        defaultConversionService.addConverter(String.class, Date.class, new StringToDateConverter("yyyy-MM-dd HH:mm:ss"));
        configConversionService(defaultConversionService);
        return defaultConversionService;
    }

    public void configConversionService(DefaultConversionService defaultConversionService) {

    }

}
