package com.hongdee.atlas.demo.mapper;

import com.hongdee.atlas.demo.entity.Demo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface DemoMapper {
    @Select("Select * from sys_demo where id=#{id}")
    Demo getOne(@Param("id") String id);

    @SelectProvider(type = com.hongdee.atlas.demo.mapper.provider.DemoMapper.class,method = "getByName")
    Demo getById(String id);
}
