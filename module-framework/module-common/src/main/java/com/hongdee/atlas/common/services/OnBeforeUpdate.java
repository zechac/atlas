package com.hongdee.atlas.common.services;

import com.hongdee.atlas.common.jpa.entity.SuperEntity;

/**
 * Created by Administrator on 2016/10/17/017.
 */
public interface OnBeforeUpdate<T extends SuperEntity> {
    void beforeUpdate(T t);
}
