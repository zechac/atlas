package org.zechac.atlas.common.convert;

import org.zechac.atlas.common.jpa.entity.StringId;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Administrator on 2016/11/28/028.
 */
public class IDEntityStringConvert implements Converter<StringId, String> {
    @Override
    public String convert(StringId source) {
        return source.getId();
    }
}
