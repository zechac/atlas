package com.hongdee.atlas.mybatis;

import lombok.Data;

@Data
public class ConditionSegment {

    private JavaCondition condition;

    /**
     * condition str
     */
    private String str;

}
