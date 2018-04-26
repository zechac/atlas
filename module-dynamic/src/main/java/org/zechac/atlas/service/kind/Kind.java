package org.zechac.atlas.service.kind;

import org.zechac.atlas.entity.dynamic.ParamsCollection;
import org.zechac.atlas.entity.dynamic.ParamsCollection;

public interface Kind {
    String SqlSelectList = "sql-select-list";
    String SqlSelectOne = "sql-select-One";
    String ParamsFilter = "params-filter";
    String MybatisSelectList = "mybatis-select-list";

    void exec(ParamsCollection collection);
}
