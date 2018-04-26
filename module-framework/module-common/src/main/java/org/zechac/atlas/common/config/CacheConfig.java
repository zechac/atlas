package org.zechac.atlas.common.config;

import org.zechac.atlas.common.cache.CacheManager;
import org.zechac.atlas.common.cache.CacheProvider;
import org.zechac.atlas.common.cache.HashMapCacheProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Profile("cache")
public class CacheConfig {

    @Bean
    @ConditionalOnMissingBean(CacheProvider.class)
    public CacheProvider cacheProvider() {
        CacheProvider cacheProvider = new HashMapCacheProvider();
        return cacheProvider;
    }

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager cacheManager(CacheProvider cacheProvider) {
        CacheManager cacheManager = new CacheManager();
        cacheManager.setCacheProvider(cacheProvider);
        return cacheManager;
    }
}
