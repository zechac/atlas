package org.zechac.atlas.common.jpa.query;

import org.zechac.atlas.common.exception.JpaMapQueryException;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaozh on 2016/10/13/013.
 */
public class StringMapQueryBuilder extends AbstractMapQueryBuilder {

    /**
     * 查询条件分组
     *
     * @param cb
     * @param predicate
     * @param combCondition
     * @param split
     */
    public void execGroupCondition(CriteriaBuilder cb, Predicate predicate, Map combCondition, String split) {
        String[] groupArr = split.split(SPLIT_GROUP);
        if (combCondition.containsKey(split)) {
            Predicate hp = (Predicate) combCondition.get(split);
            QueryCondition queryCondition1 = QueryCondition.valueOf(groupArr[0]);
            Predicate _p = null;
            switch (queryCondition1) {
                case AND:
                    _p = cb.and(hp, predicate);
                    break;
                case OR:
                    _p = cb.or(hp, predicate);
                    break;
                default:
                    throw new JpaMapQueryException("不支持的关系表达式");
            }
            combCondition.put(split, _p);
        } else {
            combCondition.put(split, predicate);
        }
    }

    private Predicate createInCondition(CriteriaBuilder criteriaBuilder, Path path, Object test) {
        CriteriaBuilder.In inCondiation = criteriaBuilder.in(path);
        if (Collection.class.isAssignableFrom(test.getClass())) {
            Collection collection = (Collection) test;
            for (Object o : collection) {
                o = checkAndConvertElementType(o, path);
                inCondiation.value(o);
            }
        } else if (test instanceof Array) {
            int le = Array.getLength(test);
            for (int i = 0; i < le; i++) {
                Object o = Array.get(test, i);
                o = checkAndConvertElementType(o, path);
                inCondiation.value(o);
            }
        } else if (path.getJavaType().isAssignableFrom(test.getClass())) {
            inCondiation.value(test);
        } else {
            String[] sval = test.toString().split(",");
            for (String s : sval) {
                Object o = checkAndConvertElementType(s, path);
                inCondiation.value(o);
            }
        }
        return inCondiation;
    }

    @Override
    public LinkedHashMap<String, Object> buildQueryMap(LinkedHashMap key, Map value) {
        if (value == null || key == null) {
            throw new JpaMapQueryException();
        }
        LinkedHashMap<String, Object> queryConditions = key;
        LinkedHashMap<String, Object> queryMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> s : queryConditions.entrySet()) {
            if (value.containsKey(s.getKey())) {
                queryMap.put(s.getValue().toString(), value.get(s.getKey()));
            }
        }
        return queryMap;
    }

    /**
     * 生成predicate
     *
     * @param cb
     * @param root
     * @param path      mapQuery key
     * @param condition val
     * @return
     */
    @Override
    public Predicate buildMapQueryPredicate(CriteriaBuilder cb, Root root, String path, String condition, Object val) {
        Path queryPath = findQueryPath(path, root);
        Object testCondition = val;
        QueryCondition queryCondition = QueryCondition.EQ;
        if (StringUtils.isNotBlank(condition)) {
            queryCondition = QueryCondition.valueOf(condition);
        }
        Predicate predicate = null;
        if (!QueryCondition.IN.equals(queryCondition)) {
            testCondition = checkAndConvertElementType(val, queryPath);
            switch (queryCondition) {
                case EQ:
                    predicate = cb.equal(queryPath, testCondition);
                    break;
                case NEQ:
                    predicate = cb.notEqual(queryPath, testCondition);
                    break;
                case GT:
                    if (Comparable.class.isAssignableFrom(testCondition.getClass())) {
                        predicate = cb.greaterThan(queryPath, (Comparable) testCondition);
                    } else {
                        throw new JpaMapQueryException(String.format("无法比较的值[%s=%s]", queryPath.getJavaType(), val.toString()));
                    }
                    break;
                case LT:
                    if (Comparable.class.isAssignableFrom(testCondition.getClass())) {
                        predicate = cb.lessThan(queryPath, (Comparable) testCondition);
                    } else {
                        throw new JpaMapQueryException(String.format("无法比较的值[%s=%s]", queryPath.getJavaType(), val.toString()));
                    }
                    break;
                case NULL:
                    predicate = cb.isNull(queryPath);
                    break;
                case NOTNULL:
                    predicate = cb.isNotNull(queryPath);
                    break;
                case LIKE:
                    predicate = cb.like(queryPath, testCondition.toString());
                    break;
                case MLIKE:
                    predicate = cb.like(queryPath, "%" + testCondition.toString() + "%");
                    break;
                case LLIKE:
                    predicate = cb.like(queryPath, "%" + testCondition.toString());
                    break;
                case RLIKE:
                    predicate = cb.like(queryPath, testCondition.toString() + "%");
                    break;
                case NLIKE:
                    predicate = cb.notLike(queryPath, testCondition.toString());
                    break;
                case LTE:
                    if (Comparable.class.isAssignableFrom(testCondition.getClass())) {
                        predicate = cb.lessThanOrEqualTo(queryPath, (Comparable) testCondition);
                    } else {
                        throw new JpaMapQueryException(String.format("无法比较的值[%s=%s]", queryPath.getJavaType(), val.toString()));
                    }
                    break;
                case GTE:
                    if (Comparable.class.isAssignableFrom(testCondition.getClass())) {
                        predicate = cb.greaterThanOrEqualTo(queryPath, (Comparable) testCondition);
                    } else {
                        throw new JpaMapQueryException(String.format("无法比较的值[%s=%s]", queryPath.getJavaType(), val.toString()));
                    }
                    break;
                case BWA:
                    List list = (List) testCondition;
                    predicate = cb.between(queryPath, (Comparable) list.get(0), (Comparable) list.get(1));
                    break;
                default:
                    throw new JpaMapQueryException("不支持的条件表达式");
            }
        } else {
            predicate = createInCondition(cb, queryPath, testCondition);
        }
        return predicate;
    }

    @Override
    public Predicate buildMapQueryPredicates(CriteriaBuilder cb, Root root, Map<String, Object> queryConditions) {
        Map<String, Predicate> combCondition = new LinkedHashMap<>();
        int x = 0;
        for (Map.Entry<String, Object> s : queryConditions.entrySet()) {
            x++;
            String predicateS = s.getKey();
            String[] split = predicateS.split(SPLIT_CONDITION);
            String condition = "";
            if (split.length > 1) {
                condition = split[1];
            }
            Predicate predicate = buildMapQueryPredicate(cb, root, split[0], condition, s.getValue());
            if (split.length > 2) {
                execGroupCondition(cb, predicate, combCondition, split[2]);
            } else {
                combCondition.put("AND" + SPLIT_GROUP + x, predicate);
            }
        }
        Predicate ret = null;
        x = 0;
        for (Map.Entry<String, Predicate> entry : combCondition.entrySet()) {
            if (ret != null) {
                if (entry.getKey().startsWith("OR")) {
                    ret = cb.or(ret, entry.getValue());
                } else {
                    ret = cb.and(ret, entry.getValue());
                }
            } else {
                ret = entry.getValue();
            }
        }
        return ret;
    }
}
