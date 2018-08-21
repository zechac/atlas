package org.zechac.atlas.common.jpa.convert;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;

public class StringAndJSONArrayConverter implements AttributeConverter<JSONArray, String> {
    @Override
    public String convertToDatabaseColumn(JSONArray attribute) {
        if (attribute != null) {
            return attribute.toJSONString();
        }
        return null;
    }

    @Override
    public JSONArray convertToEntityAttribute(String dbData) {
        if (StringUtils.isNotBlank(dbData)) {
            return JSONArray.parseArray(dbData);
        } else {
            return new JSONArray();
        }
    }
}