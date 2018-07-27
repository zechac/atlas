package org.zechac.atlas.demo.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class DemoMapper {

    public String getByName() {
        String sql = new SQL().SELECT("*")
                .FROM("sys_demo")
                .WHERE("id=#{id}").toString();
        return sql;
    }
}
