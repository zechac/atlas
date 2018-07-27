package org.zechac.atlas.demo.mapper;

import org.zechac.atlas.demo.entity.Demo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.zechac.atlas.demo.entity.Demo;

public interface DemoMapper {
    @Select("Select * from sys_demo where id=#{id}")
    Demo getOne(@Param("id") String id);

    @SelectProvider(type = org.zechac.atlas.demo.mapper.provider.DemoMapper.class, method = "getByName")
    Demo getById(String id);
}
