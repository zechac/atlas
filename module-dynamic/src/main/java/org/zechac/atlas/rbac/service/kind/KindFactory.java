package org.zechac.atlas.rbac.service.kind;

import com.alibaba.fastjson.JSONObject;
import org.zechac.atlas.common.exception.AtlasException;
import org.zechac.atlas.common.jdbc.template.CommonQuery;
import org.zechac.atlas.mybatis.mapper.MybatisQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KindFactory {
    @Autowired
    private CommonQuery commonQuery;

    @Autowired
    private MybatisQuery mybatisQuery;

    @SuppressWarnings("unchecked")
    public <T> T get(JSONObject object) {
        String kind = object.get("kind").toString();
        switch (kind) {
            case Kind.SqlSelectList:
                return (T) new SqlSelectList(object, commonQuery);
            case Kind.SqlSelectOne:
                return (T) new SqlSelectOne(object, commonQuery);
            case Kind.ParamsFilter:
                return (T) new ParamsFilter(object);
            case Kind.MybatisSelectList:
                return (T) new MybatisSelect(object, mybatisQuery);
            default:
                throw new AtlasException(KindFactory.class, "暂未实现");
        }
    }
}
