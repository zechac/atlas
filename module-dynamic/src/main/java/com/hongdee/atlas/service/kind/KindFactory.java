package com.hongdee.atlas.service.kind;

import com.alibaba.fastjson.JSONObject;
import com.hongdee.atlas.common.exception.AtlasException;
import com.hongdee.atlas.common.jdbc.template.CommonQuery;
import com.hongdee.atlas.mybatis.mapper.MybatisQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KindFactory {
    @Autowired
    private CommonQuery commonQuery;

    @Autowired
    private MybatisQuery mybatisQuery;

    @SuppressWarnings("unchecked")
    public <T> T get(JSONObject object){
        String kind=object.get("kind").toString();
        switch (kind) {
            case Kind.SqlSelectList:
                return (T)new SqlSelectList(object,commonQuery);
            case Kind.SqlSelectOne:
                return (T)new SqlSelectOne(object,commonQuery);
            case Kind.ParamsFilter:
                return (T)new ParamsFilter(object);
            case Kind.MybatisSelectList:
                return (T)new MybatisSelect(object,mybatisQuery);
            default:
                throw new AtlasException(KindFactory.class,"暂未实现");
        }
    }
}
