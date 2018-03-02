package com.hongdee.atlas.common.convert;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Administrator on 2016/11/18/018.
 */
@Slf4j
public class StringJSONObjectConvert implements Converter<String,JSONObject> {
    @Override
    public JSONObject convert(String s) {
        if(StringUtils.isNotBlank(s)){
            try {
                return JSONObject.parseObject(s);
            }catch (Exception e){
                log.warn(e.getMessage());
            }
        }
        return null;
    }
}
