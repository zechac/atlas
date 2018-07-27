package org.zechac.atlas.service.kind;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONType;
import org.zechac.atlas.entity.dynamic.ParamsCollection;
import org.zechac.atlas.mybatis.SelectTemplate;
import org.zechac.atlas.mybatis.mapper.MybatisQuery;
import lombok.Data;
import org.zechac.atlas.entity.dynamic.ParamsCollection;
import org.zechac.atlas.mybatis.SelectTemplate;
import org.zechac.atlas.mybatis.mapper.MybatisQuery;

import java.util.List;

/**
 * mybatis-query
 * {
 * kind:"mybatis-query",
 * template:{
 * select:[] //select template
 * },
 * name:"step1"
 * }
 *
 * @author zhaozh
 */
@Data
@JSONType(ignores = {"commonQuery"})
public class MybatisSelect implements Kind {

    private MybatisQuery mybatisQuery;

    public MybatisSelect(JSONObject object, MybatisQuery mybatisQuery) {
        if (object.containsKey("name")) {
            this.name = object.getString("name");
        }
        this.template = object.getObject("template", SelectTemplate.class);
        this.mybatisQuery = mybatisQuery;
    }

    private String kind = MybatisSelectList;

    private SelectTemplate template;

    private String name;

    @Override
    public void exec(ParamsCollection collection) {
        List rt = mybatisQuery.queryList(template, collection);
        collection.put(name, rt);
    }
}
