package com.hongdee.atlas.common.web.converter;

import java.util.Map;

/**
 * Created by Administrator on 2016/10/12/012.
 */
public interface JSONCustomConverter<T> {
    /**
     * 要转换的property
     * @return
     */
    String[] getProperty();

    /**
     * 转换要转换的property时调用
     * @param resource 实体
     * @param prop      属性
     * @param val       值
     * @param dist      生成的map
     */
    void converter(T resource, String prop, Object val, Map dist);
}
