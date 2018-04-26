package org.zechac.atlas.common.cache;

import org.springframework.cache.Cache;

public interface CacheProvider extends Cache {

    boolean hasValue(String key);

    <T> T getByKey(String key);

}
