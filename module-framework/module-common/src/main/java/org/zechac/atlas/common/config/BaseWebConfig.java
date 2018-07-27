package org.zechac.atlas.common.config;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-5-5.
 */
@Configuration
@EnableWebMvc
public class BaseWebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    Environment environment;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }

    /**
     * MessageConvert
     *
     * @return
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter4();
        List list = new ArrayList();
        list.add(MediaType.APPLICATION_JSON);
        list.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(list);
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        fastJsonConfig.setFeatures(Feature.DisableCircularReferenceDetect);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        BufferedImageHttpMessageConverter bufferedImageHttpMessageConverter = new BufferedImageHttpMessageConverter();
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        converters.add(stringHttpMessageConverter);
        converters.add(bufferedImageHttpMessageConverter);
        converters.add(byteArrayHttpMessageConverter);
        converters.add(fastJsonHttpMessageConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration handlerRegistration = registry.addResourceHandler("resources/**", "/favicon.ico");
        handlerRegistration.addResourceLocations("resources/", "/favicon.ico");
    }

    /**
     * 必须这个名！
     *
     * @return
     */
    @Bean(name = "multipartResolver")
    public StandardServletMultipartResolver standardServletMultipartResolver() {
        StandardServletMultipartResolver standardServletMultipartResolver = new StandardServletMultipartResolver();
        return standardServletMultipartResolver;
    }
}
