package org.zechac.atlas.rbac.service.kind;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONType;
import org.zechac.atlas.common.jdbc.template.CommonQuery;
import org.zechac.atlas.rbac.entity.dynamic.ParamsCollection;
import lombok.Data;

import java.util.List;

/**
 * sql-select-list
 * {
 * kind:"sql-select-list",
 * sql:"select * from table where a=#{a},
 * name:"step1"
 * }
 *
 * @author zhaozh
 */
@Data
@JSONType(ignores = {"commonQuery"})
public class SqlSelectList implements Kind {

    public SqlSelectList(JSONObject object, CommonQuery commonQuery) {
        if (object.containsKey("name")) {
            this.name = object.getString("name");
        }
        this.sql = object.getString("sql");
        this.commonQuery = commonQuery;
    }

    private CommonQuery commonQuery;

    private String kind = SqlSelectList;

    private String sql;

    private String name;

    @Override
    public void exec(ParamsCollection collection) {
        List ret = commonQuery.queryListByMap(sql, collection);
        collection.put(name, ret);
    }
}
