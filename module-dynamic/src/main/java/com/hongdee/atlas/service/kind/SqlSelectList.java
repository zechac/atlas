package com.hongdee.atlas.service.kind;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONType;
import com.hongdee.atlas.common.jdbc.template.CommonQuery;
import com.hongdee.atlas.entity.dynamic.ParamsCollection;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * sql-select-list
 * {
 *     kind:"sql-select-list",
 *     sql:"select * from table where a=#{a},
 *     name:"step1"
 * }
 * @author zhaozh
 */
@Data
@JSONType(ignores = {"commonQuery"})
public class SqlSelectList implements Kind{

    public SqlSelectList(JSONObject object, CommonQuery commonQuery){
        if(object.containsKey("name")){
            this.name=object.getString("name");
        }
        this.sql=object.getString("sql");
        this.commonQuery=commonQuery;
    }

    private CommonQuery commonQuery;

    private String kind=Kind.SqlSelectList;

    private String sql;

    private String name;

    @Override
    public void exec(ParamsCollection collection) {
        List ret= commonQuery.queryListByMap(sql,collection);
        collection.put(name,ret);
    }
}
