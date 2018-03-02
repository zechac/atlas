package com.hongdee.atlas.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.data.domain.Page;
import com.hongdee.atlas.common.web.converter.JSONCustomConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/24/024.
 */
@Slf4j
public class CommonUtils {
    /**
     * 自定义json字段
     *
     * @param props
     * @param <T>
     * @return
     */
    public static <T> Map objectToMap(T t, Object... props) {
        Map map = new HashMap();
        for (int i = 0; i < props.length; i++) {
            if (props[i] instanceof String) {
                String prop = (String) props[i];
                Object val = "";
                try {
                    if("*".equals(prop)){
                        map=PropertyUtils.describe(t);
                        continue;
                    }
                    val = PropertyUtils.getProperty(t, prop);
                    if (val == null) {
                        val = "";
                    }
                    map.put(prop, val);
                } catch (Exception e) {
                    log.warn("Bean Convert Exceptions:"+e.getMessage());
                }
            } else if (JSONCustomConverter.class.isAssignableFrom(props[i].getClass())) {
                JSONCustomConverter jsonCustomConverter = (JSONCustomConverter) props[i];
                String[] props1 = jsonCustomConverter.getProperty();
                for (String pro : props1) {
                    try {
                        Object val = PropertyUtils.getProperty(t, pro);
                        jsonCustomConverter.converter(t, pro, val, map);
                    } catch (Exception e) {
                        log.warn(e.getMessage());
                    }
                }
            }
        }
        return map;
    }

    /**
     * 自定义json字段
     *
     * @param list
     * @param props
     * @param <T>
     * @return
     */
    public static <T> List objectListToMapList(List<T> list, Object... props) {
        List ret = new ArrayList();
        for (T t : list) {
            Map map = objectToMap(t, props);
            ret.add(map);
        }
        return ret;
    }

    /**
     * page 转 map
     *
     * @param page
     * @return
     */
    public static Map pageToMap(Page page, Object... props) {
        Map ret = new HashMap();
        ret.put("total", page.getTotalElements());
        ret.put("page", page.getNumber());
        ret.put("size", page.getSize());
        if (props != null) {
            ret.put("content", objectListToMapList(page.getContent(), props));
        } else {
            ret.put("content", page.getContent());
        }
        return ret;
    }

    /**
     * page 转 map
     *
     * @param page
     * @return
     */
    public static Map pageToMap(Page page) {
        Map ret = new HashMap();
        ret.put("total", page.getTotalElements());
        ret.put("page", page.getNumber());
        ret.put("size", page.getSize());
        ret.put("content", page.getContent());
        return ret;
    }
}
