package org.zechac.atlas.common.convert;

import org.zechac.atlas.common.jpa.entity.IDEntity;
import org.zechac.atlas.common.jpa.entity.IDEntity;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Administrator on 2016/11/28/028.
 */
public class IDEntityStringConvert implements Converter<IDEntity, String> {
    @Override
    public String convert(IDEntity source) {
        return source.getId();
    }
}
