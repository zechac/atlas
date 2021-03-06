package org.zechac.atlas.mybatis.mapper;

import org.zechac.atlas.mybatis.SelectTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

public interface MybatisQuery {


    /**
     * sql select
     *
     * @param sql
     * @param value
     * @return
     */
    @SelectProvider(type = org.zechac.atlas.mybatis.mapper.provider.MybatisQuery.class, method = "queryList")
    List<Map> queryList(SelectTemplate sql, @Param("param") Map value);

}
