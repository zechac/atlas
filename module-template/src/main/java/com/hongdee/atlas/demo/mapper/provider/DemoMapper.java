package com.hongdee.atlas.demo.mapper.provider;

import com.hongdee.atlas.demo.entity.Demo;
import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;
import java.util.Map;

public class DemoMapper {

    public String getByName(){
        String sql= new SQL().SELECT("*")
                .FROM("sys_demo")
                .WHERE("id=#{id}").toString();
        return sql;
    }
}
