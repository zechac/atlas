package com.hongdee.atlas.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.persistence.EntityManagerFactory;

/**
 * Created by Administrator on 2016/10/18/018.
 * open session in view
 * EnableSpringDataWebSupport
 */
@Configuration
@EnableSpringDataWebSupport
public abstract class JpaSupportWebConfig extends BaseWebConfig {
    @Autowired
    @Qualifier("primaryEntityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor=new OpenEntityManagerInViewInterceptor();
        openEntityManagerInViewInterceptor.setEntityManagerFactory(entityManagerFactory);
        registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor);
    }
}
