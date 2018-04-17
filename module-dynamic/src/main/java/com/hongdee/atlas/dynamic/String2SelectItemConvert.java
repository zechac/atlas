package com.hongdee.atlas.dynamic;

import com.alibaba.fastjson.JSON;

import javax.persistence.AttributeConverter;
import java.util.List;

public class String2SelectItemConvert implements AttributeConverter<List<SelectItem>, String> {
    @Override
    public String convertToDatabaseColumn(List<SelectItem> attribute) {
        return JSON.toJSONString(attribute);
    }

    @Override
    public List<SelectItem> convertToEntityAttribute(String dbData) {
        return JSON.parseArray(dbData,SelectItem.class);
    }
}
