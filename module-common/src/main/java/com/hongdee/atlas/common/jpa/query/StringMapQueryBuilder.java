package com.hongdee.atlas.common.jpa.query;

import com.hongdee.atlas.common.exception.JpaMapQueryException;

import javax.persistence.criteria.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by zhaozh on 2016/10/13/013.
 */
public class StringMapQueryBuilder extends AbstractMapQueryBuilder {

    /**
     * 查询条件分组
     * @param cb
     * @param predicate
     * @param combCondition
     * @param split
     */
    public void execGroupCondition(CriteriaBuilder cb,Predicate predicate,Map combCondition,String... split){
        String[] groupArr=split[2].split(SPLIT_GROUP);
        if(combCondition.containsKey(groupArr[1])) {
            Predicate hp = (Predicate) combCondition.get(groupArr[1]);
            QueryCondition queryCondition1=QueryCondition.valueOf(groupArr[0]);
            Predicate _p=null;
            switch (queryCondition1){
                case AND:
                    _p=cb.and(hp,predicate);
                    break;
                case OR:
                    _p=cb.or(hp,predicate);
                    break;
                default:
                    throw new JpaMapQueryException("不支持的关系表达式");
            }
            combCondition.put(groupArr[1],_p);
        }else{
            combCondition.put(groupArr[1], predicate);
        }
    }

    private Predicate createInCondition(CriteriaBuilder criteriaBuilder,Path path,Object test ) {
        CriteriaBuilder.In inCondiation = criteriaBuilder.in(path);
        if (Collection.class.isAssignableFrom(test.getClass())) {
            Collection collection = (Collection) test;
            for (Object o : collection) {
                o = checkAndConvertElementType(o, path);
                inCondiation.value(o);
            }
        }else if(test instanceof Array){
            int le= Array.getLength(test);
            for(int i=0;i<le;i++){
                Object o=Array.get(test,i);
                o = checkAndConvertElementType(o, path);
                inCondiation.value(o);
            }
        } else if (path.getJavaType().isAssignableFrom(test.getClass())) {
            inCondiation.value(test);
        } else {
            String[] sval = test.toString().split(",");
            for (String s : sval) {
                Object o = checkAndConvertElementType(s,path);
                inCondiation.value(o);
            }
        }
        return inCondiation;
    }

    @Override
    public LinkedHashMap<String,Object> buildQueryMap(LinkedHashMap key, Map value) {
        if(value==null||key==null){
            throw new JpaMapQueryException();
        }
        LinkedHashMap<String,Object> queryConditions=key;
        LinkedHashMap<String,Object> queryMap=new LinkedHashMap<>();
        for(Map.Entry<String,Object> s:queryConditions.entrySet()) {
            if (value.containsKey(s.getKey())) {
                queryMap.put(s.getValue().toString(), value.get(s.getKey()));
            }
        }
        return queryMap;
    }

    /**
     * 生成predicate
     * @param cb
     * @param root
     * @param key   mapQuery key
     * @param val   val
     * @return
     */
    @Override
    public Predicate buildMapQueryPredicate(CriteriaBuilder cb, Root root, String key, Object val) {
        String[] split=key.split(SPLIT_CONDITON);
        Path condition =findQueryPath(split[0],root);
        Object testCondition=val;
        QueryCondition queryCondition=QueryCondition.EQ;
        if(split.length>1) {
            queryCondition = QueryCondition.valueOf(split[1]);
        }
        Predicate predicate = null;
        if(!QueryCondition.IN.equals(queryCondition)) {
            testCondition=checkAndConvertElementType(val,condition);
            switch (queryCondition) {
                case EQ:
                    predicate = cb.equal(condition,testCondition);
                    break;
                case NEQ:
                    predicate = cb.notEqual(condition,testCondition);
                    break;
                case GT:
                    if(Comparable.class.isAssignableFrom(testCondition.getClass())){
                        predicate=cb.greaterThan(condition,(Comparable)testCondition);
                    }else {
                        throw new JpaMapQueryException(String.format("无法比较的值[%s=%s]",condition.getJavaType(),val.toString()));
                    }
                    break;
                case LT:
                    if(Comparable.class.isAssignableFrom(testCondition.getClass())){
                        predicate=cb.lessThan(condition,(Comparable)testCondition);
                    }else {
                        throw new JpaMapQueryException(String.format("无法比较的值[%s=%s]",condition.getJavaType(),val.toString()));
                    }
                    break;
                case NULL:
                    predicate=cb.isNull(condition);
                    break;
                case NOTNULL:
                    predicate=cb.isNotNull(condition);
                    break;
                case LIKE:
                    predicate=cb.like(condition,testCondition.toString());
                    break;
                case MLIKE:
                    predicate=cb.like(condition,"%"+testCondition.toString()+"%");
                    break;
                case LLIKE:
                    predicate=cb.like(condition,"%"+testCondition.toString());
                    break;
                case RLIKE:
                    predicate=cb.like(condition,testCondition.toString()+"%");
                    break;
                case NLIKE:
                    predicate=cb.notLike(condition,testCondition.toString());
                    break;
                case LTE:
                    if(Comparable.class.isAssignableFrom(testCondition.getClass())){
                        predicate=cb.lessThanOrEqualTo(condition,(Comparable)testCondition);
                    }else {
                        throw new JpaMapQueryException(String.format("无法比较的值[%s=%s]",condition.getJavaType(),val.toString()));
                    }
                    break;
                case GTE:
                    if(Comparable.class.isAssignableFrom(testCondition.getClass())){
                        predicate=cb.greaterThanOrEqualTo(condition,(Comparable)testCondition);
                    }else {
                        throw new JpaMapQueryException(String.format("无法比较的值[%s=%s]",condition.getJavaType(),val.toString()));
                    }
                    break;
                case BWA:
                    List list=(List)testCondition;
                    predicate=cb.between(condition,(Comparable) list.get(0),(Comparable) list.get(1));
                    break;
                default:
                    throw new JpaMapQueryException("不支持的条件表达式");
            }
        }else {
            predicate=createInCondition(cb,condition,testCondition);
        }
        return predicate;
    }

    @Override
    public Predicate[] buildMapQueryPredicates(CriteriaBuilder cb, Root root, Map<String,Object> queryConditions) {
        Map<String,Predicate> combCondition=new HashMap();
        int x=0;
        for (Map.Entry<String,Object> s : queryConditions.entrySet()) {
            x++;
            String predicateS =s.getKey();
            Predicate predicate = buildMapQueryPredicate(cb,root,predicateS,s.getValue());
            String[] split=predicateS.split(SPLIT_GROUP);
            if (split.length > 2) {
                execGroupCondition(cb,predicate,combCondition,split);
            }else {
                combCondition.put(SPLIT_GROUP +x,predicate);
            }
        }

        Predicate[] predicteArr=new Predicate[combCondition.keySet().size()];
        x=0;
        for(Predicate s:combCondition.values()){
            predicteArr[x++]=s;
        }
        return predicteArr;
    }
}
