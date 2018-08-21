package org.zechac.atlas.common.services;

import org.zechac.atlas.common.jpa.entity.SuperEntity;

/**
 * Created by Administrator on 2016/10/17/017.
 */
public interface OnBeforeSave<T extends SuperEntity> {
    void beforeSave(T superEntity);
}
