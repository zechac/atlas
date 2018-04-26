package org.zechac.atlas.common.web.converter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Deprecated
public class JSONPMessageConverter extends FastJsonHttpMessageConverter {

    public JSONPMessageConverter() {
        super();
    }

}