package org.zechac.atlas.service.kind;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONType;
import org.zechac.atlas.common.jdbc.template.CommonQuery;
import org.zechac.atlas.entity.dynamic.ParamsCollection;
import lombok.Data;
import org.zechac.atlas.common.jdbc.template.CommonQuery;
import org.zechac.atlas.entity.dynamic.ParamsCollection;

import java.util.Map;

/**
 * sql-select-one
 * {
 * kind:"sql-select-one",
 * sql:"select * from table where a=#{a} limit 1,
 * name:"step1"
 * }
 *
 * @author zhaozh
 */
@Data
@JSONType(ignores = {"commonQuery"})
public class SqlSelectOne implements Kind {
    public SqlSelectOne(JSONObject object, CommonQuery commonQuery) {
        if (object.containsKey("name")) {
            this.name = object.getString("name");
        }
        this.sql = object.getString("sql");
        this.commonQuery = commonQuery;
    }

    private CommonQuery commonQuery;

    private String kind = SqlSelectOne;

    private String sql;

    private String name;

    @Override
    public void exec(ParamsCollection collection) {
        Map ret = commonQuery.queryOneByMap(sql, collection);
        collection.put(name, ret);
    }
}
