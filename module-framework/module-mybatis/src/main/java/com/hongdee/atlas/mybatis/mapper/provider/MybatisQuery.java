package com.hongdee.atlas.mybatis.mapper.provider;

import com.hongdee.atlas.mybatis.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import javax.persistence.criteria.From;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MybatisQuery {

    public String queryList(SelectTemplate selectTemplate,@Param("param") Map data){
        SQL sql=new SQL();
        sql.SELECT(buildSelect(selectTemplate.getSelect(),data));
        sql.FROM(buildFrom(selectTemplate.getFrom(),data));
        buildWhere(sql,selectTemplate.getWhere(),data);
        return sql.toString();
    }

    public void buildWhere(SQL sql, List<ConditionSegment> where,Map data){
        for(ConditionSegment conditionSegment:where){
            if(test(conditionSegment.getCondition(),data)){
                sql.WHERE(conditionSegment.getStr());
            }
        }
    }

    public String[] buildSelect(List<SelectSegment> selectSegments, Map data){
        List<String> rt=new ArrayList<>();
        for(SelectSegment selectSegment:selectSegments){
            if(test(selectSegment.getCondition(),data)){
                rt.add(selectSegment.getStr());
            }
        }
        return rt.toArray(new String[rt.size()]);
    }

    public String[] buildFrom(List<FromSegment> fromSegments,Map data){
        List<String> rt=new ArrayList<>();
        for(FromSegment from:fromSegments){
            if(test(from.getCondition(),data)){
                rt.add(from.getStr());
            }
        }
        return rt.toArray(new String[rt.size()]);
    }

    private boolean test(JavaCondition javaCondition,Map data){
        return true;
    }
}
