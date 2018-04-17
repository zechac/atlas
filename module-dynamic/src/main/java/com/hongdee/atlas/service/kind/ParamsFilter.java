package com.hongdee.atlas.service.kind;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONType;
import com.hongdee.atlas.common.jdbc.template.CommonQuery;
import com.hongdee.atlas.entity.dynamic.ParamsCollection;
import lombok.Data;

import java.util.List;

/**
 * params-filter
 * {
 *     kind:"params-filter",
 *     filter:["prop","props.prop"],
 *     name:"step1"
 * }
 * @author zhaozh
 */
@Data
@JSONType(ignores = {"commonQuery"})
public class ParamsFilter implements Kind {
    public ParamsFilter(JSONObject object){
        if(object.containsKey("name")){
            this.name=object.getString("name");
        }
        this.filter=object.getJSONArray("filter");
    }

    private String kind=Kind.ParamsFilter;

    private List filter;

    private String name;

    @Override
    public void exec(ParamsCollection collection) {
        ParamsCollection paramsCollection=new ParamsCollection();
        for(Object s : filter){
            paramsCollection.put(s,collection.get(s));
        }
        collection.clear();
        collection.putAll(paramsCollection);
    }
}
