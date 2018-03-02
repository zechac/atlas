package com.hongdee.atlas.common.jpa.convert;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;

/**
 * Created by zhaozh on 2016/11/18/018.
 */
@Slf4j
public class StringAndJSONObjectConvert implements AttributeConverter<JSONObject, String> {
    @Override
    public String convertToDatabaseColumn(JSONObject s) {
        if(s==null){
            return null;
        }
       return s.toJSONString();
    }

    @Override
    public JSONObject convertToEntityAttribute(String list) {
       try{
           return JSONObject.parseObject(list);
       }catch (Exception e){
           log.warn(e.getMessage());
           return new JSONObject();
       }
    }
}
