package com.hongdee.atlas.common.config;

import com.hongdee.atlas.common.cache.CacheManager;
import com.hongdee.atlas.common.cache.CacheProvider;
import com.hongdee.atlas.common.cache.HashMapCacheProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@Profile("cache")
public class CacheConfig {

    @Bean
    @ConditionalOnMissingBean(CacheProvider.class)
    public CacheProvider cacheProvider(){
        CacheProvider cacheProvider=new HashMapCacheProvider();
        return cacheProvider;
    }

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager cacheManager(CacheProvider cacheProvider){
        CacheManager cacheManager=new CacheManager();
        cacheManager.setCacheProvider(cacheProvider);
        return cacheManager;
    }
}
