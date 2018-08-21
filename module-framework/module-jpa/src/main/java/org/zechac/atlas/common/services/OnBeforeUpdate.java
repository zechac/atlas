package org.zechac.atlas.common.services;

import org.zechac.atlas.common.jpa.entity.SuperEntity;

/**
 * Created by Administrator on 2016/10/17/017.
 */
public interface OnBeforeUpdate<T extends SuperEntity> {
    void beforeUpdate(T t);
}
