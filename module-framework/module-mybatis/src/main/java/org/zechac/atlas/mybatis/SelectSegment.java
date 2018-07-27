package org.zechac.atlas.mybatis;

import lombok.Data;

@Data
public class SelectSegment {

    private JavaCondition condition;

    private String str;
}
