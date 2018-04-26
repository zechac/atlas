package org.zechac.atlas.common.cache;

import lombok.Data;
import org.springframework.cache.Cache;

import java.util.Collection;

@Data
public class CacheManager implements org.springframework.cache.CacheManager {

    private CacheProvider cacheProvider;

    public void CacheManager(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public <T> T getByKey(String key) {
        return cacheProvider.getByKey(key);
    }

    public <T> void save(String key, T value) {
        cacheProvider.put(key, value);
    }

    @Override
    public Cache getCache(String name) {
        return null;
    }

    @Override
    public Collection<String> getCacheNames() {
        return null;
    }
}
