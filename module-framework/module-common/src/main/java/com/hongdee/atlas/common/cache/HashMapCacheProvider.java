package com.hongdee.atlas.common.cache;

import lombok.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class HashMapCacheProvider implements CacheProvider {

    @Override
    public String getName() {
        return "HashMapCacheProvider";
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    public ValueWrapper get(Object key) {
        return cache.get(key);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        HashMapValueWapper<T> valueWrapper=cache.get(key);
        if(valueWrapper!=null){
            return valueWrapper.val;
        }
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        HashMapValueWapper hashMapValueWapper=new HashMapValueWapper();
        hashMapValueWapper.val=value;
        hashMapValueWapper.createTime=new Date();
        cache.put(key,hashMapValueWapper);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        if(cache.containsKey(key)){
            return null;
        }
        HashMapValueWapper hashMapValueWapper=new HashMapValueWapper();
        hashMapValueWapper.val=value;
        hashMapValueWapper.createTime=new Date();
        cache.put(key,hashMapValueWapper);
        return hashMapValueWapper;
    }

    @Override
    public void evict(Object key) {

    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Data
    public static class HashMapValueWapper<T> implements ValueWrapper{
        public T val;
        public Date createTime;

        @Override
        public Object get() {
            return val;
        }
    }

    private Map<Object,HashMapValueWapper> cache=new HashMap<>();

    @Override
    public boolean hasValue(String key) {
        return cache.containsKey(key);
    }

    @Override
    public <T> T getByKey(String key) {
        HashMapValueWapper<T> val=cache.get(key);
        if(val!=null){
            return val.val;
        }
        return null;
    }

}
