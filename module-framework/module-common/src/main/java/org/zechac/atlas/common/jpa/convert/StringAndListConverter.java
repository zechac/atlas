package org.zechac.atlas.common.jpa.convert;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaozh on 2016/11/3/003.
 * 实体与数据库之间的String-list转换器
 */
public class StringAndListConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> s) {
        String ret = "";
        if (s == null || s.size() == 0) {
            return null;
        }
        if (s.size() == 1) {
            return s.get(0);
        } else {
            int i = 0;
            for (; i < s.size() - 1; i++) {
                ret += s.get(i) + ",";
            }
            ret += s.get(i);
            return ret;
        }
    }

    @Override
    public List convertToEntityAttribute(String list) {
        List<String> l = new ArrayList<>();
        if (StringUtils.isNotBlank(list)) {
            String[] al = list.split(",");
            for (String s : al) {
                l.add(s);
            }
        }
        return l;
    }
}
