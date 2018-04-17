package com.hongdee.atlas.mybatis;

import lombok.Data;

import java.util.List;

/**
 * sql 查询 模板
 */
@Data
public class SelectTemplate {

    /**
     * select
     */
    private List<SelectSegment> select;

    /**
     * table
     */
    private List<FromSegment> from;

    private List<JoinSegment> joinSegments;

    private List<ConditionSegment> where;

    private List<String> groupBy;

}
