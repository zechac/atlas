package com.hongdee.atlas.common.jpa.query;

import org.hibernate.query.criteria.internal.path.SingularAttributePath;
import org.springframework.core.convert.ConversionService;

import javax.persistence.criteria.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhaozh on 2016/11/28/028.
 */
public abstract class AbstractMapQueryBuilder implements MapQueryBuilder<String> {

    protected static final String SPLIT_CONDITON ="\\$";
    protected static final String SPLIT_ENTITY ="__";
    protected static final String SPLIT_RELATION ="_";
    protected static final String SPLIT_GROUP ="_";

    protected ConversionService conversionService;

    public void setConversionService(ConversionService conversionService){
        this.conversionService=conversionService;
    }

    /**
     *
     * @param from
     * @param name
     * @param joinType
     * @return
     */
    private Join hasJoin(From from,String name,JoinType joinType){
        for(Object hj:from.getJoins()){
            Join join1=(Join)hj;
            if(join1.getJoinType().equals(joinType)&&join1.getAttribute().getName().equals(name)){
                return join1;
            }
        }
        return null;
    }

    protected Path findQueryPath(String pathStr, Root root){
        Path parentPath=root;
        Join join=null;
        String[] pathArr=pathStr.split(SPLIT_ENTITY);
        if(pathArr.length>1) {
            for (int i=0;i<pathArr.length-1;i++) {
                String[] _at = pathArr[i].split(SPLIT_RELATION);
                JoinType joinType = JoinType.INNER;
                if (_at.length > 1) {
                    joinType = JoinType.valueOf(_at[1]);
                }
                if (join == null) {
                    Join has=hasJoin(root,_at[0],joinType);
                    join=has==null? root.join(_at[0], joinType):has;
                } else {
                    Join has=hasJoin(join,_at[0],joinType);
                    join=has==null?join.join(_at[0], joinType):has;
                }
            }
            parentPath=join.get(pathArr[pathArr.length-1]);
        }else {
            parentPath=parentPath.get(pathStr);
        }
        return parentPath;
    }

    /**
     * PluralAttributePath 类型一般为（Set,Map,List等），这些需要关联表，不能直接使用实体查询
     *
     * @param codition
     * @return
     */
    protected Class getPathElementType(Path codition){
        if(codition instanceof SingularAttributePath){
            return codition.getJavaType();
        }else {
            throw new RuntimeException("查询路径错误。");
        }
//        if(codition instanceof PluralAttributePath){
//            PluralAttributePath pluralAttributePath=(PluralAttributePath)codition;
//            return pluralAttributePath.getAttribute().getElementType().getJavaType();
//        }else{
//            throw new NotImplementedException("later");
//        }
    }

    /**
     * 类型转换
     * @param val
     * @param condition
     * @return
     */
    protected Object checkAndConvertType(Object val,Path condition){
        Class conditionType=condition.getJavaType();
        if (conversionService != null && val != null && !conditionType.isAssignableFrom(val.getClass())) {
            if (conversionService.canConvert(val.getClass(),conditionType)) {
                return conversionService.convert(val,conditionType);
            }
        }
        return val;
    }

    /**
     * 类型转换
     * @param val
     * @param condition
     * @return
     */
    protected Object checkAndConvertElementType(Object val,Path condition){
        Class conditionType=getPathElementType(condition);
        if (conversionService != null && val != null && !conditionType.isAssignableFrom(val.getClass())) {
            if (conversionService.canConvert(val.getClass(),conditionType)) {
                return conversionService.convert(val,conditionType);
            }
        }
        return val;
    }

    @Override
    public abstract LinkedHashMap<String, Object> buildQueryMap(LinkedHashMap<Object, String> key, Map<Object, Object> value);
    @Override
    public abstract Predicate buildMapQueryPredicate(CriteriaBuilder cb, Root root,String path, String condition, Object value);
    @Override
    public abstract Predicate buildMapQueryPredicates(CriteriaBuilder cb, Root root, Map<String, Object> condition);
}
