package com.hongdee.atlas.common.jpa.query;

/**
 * Created by zhaozh on 2016/7/13.
 */
public enum QueryCondition {
    EQ  //=
    ,NEQ//!=
    ,GT //>
    ,LT //<
    ,IN // sql in
    ,AND //sql and
    ,OR //sql or
    ,NULL //sql null
    ,NOTNULL //sql notnull
    ,LIKE //sql like
    ,MLIKE //sql %like%
    ,LLIKE //sql %like
    ,RLIKE //sql like %
    ,NLIKE //sql not like
    ,LTE //<=
    ,GTE //>=
    ,BWA //between and
}
