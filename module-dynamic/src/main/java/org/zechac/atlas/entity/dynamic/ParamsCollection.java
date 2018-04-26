package org.zechac.atlas.entity.dynamic;

import org.zechac.atlas.common.exception.AtlasSqlException;

import java.util.HashMap;

/**
 * 用于service 执行过程中参数的保存
 *
 * @author zhzozh
 */
public class ParamsCollection<K, V> extends HashMap<K, V> {

    @Override
    public V put(K k, V v) {
        if (this.containsKey(k)) {
            throw new AtlasSqlException(ParamsCollection.class, "变量已存在");
        }
        return super.put(k, v);
    }
}
