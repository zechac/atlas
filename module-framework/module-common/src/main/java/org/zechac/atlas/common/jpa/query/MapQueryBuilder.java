package org.zechac.atlas.common.jpa.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhaozh on 2016/10/19/019.
 * demoRelation__R__id_LEFT$LT$OR_2
 * id$EQ$AND_2
 * LinkedHashMap linkedHashMap=new LinkedHashMap();
 * linkedHashMap.put("ideq","id$EQ");
 * linkedHashMap.put("data","data$EQ$AND_2");
 * linkedHashMap.put("data1","data1$EQ$AND_2");
 * linkedHashMap.put("data2","data2$NOTNULL$OR_2");
 * linkedHashMap.put("type3","testTypeEntity_INNER__type1__name$NOTNULL$OR_2");
 * linkedHashMap.put("type3e","testTypeEntity$EQ");
 * linkedHashMap.put("ANDT","data$EQ$AND_1");
 * linkedHashMap.put("idNotNull","id$NOTNULL");
 * linkedHashMap.put("dataNULL","data$NULL");
 * linkedHashMap.put("numEQ","num$EQ");
 * linkedHashMap.put("numIN[]","num$IN");
 * linkedHashMap.put("dataIN","data$IN");
 * linkedHashMap.put("numGT","num$GT$OR_1");
 * linkedHashMap.put("numLT","num$LT$AND_1");
 * linkedHashMap.put("dataLIKE","data$LIKE");
 * linkedHashMap.put("typeJOINEQ","testTypeEntity$EQ");
 * linkedHashMap.put("enumIN","testEnum$IN");
 * linkedHashMap.put("enumEQ","testEnum$EQ");
 * linkedHashMap.put("data2EQ","data2Ori$EQ");
 * linkedHashMap.put("data2Like","data2Ori$LIKE");
 * linkedHashMap.put("typeName","testTypeEntity__name$EQ");
 * linkedHashMap.put("typeName1","testTypeEntity__name1$EQ");
 */
public interface MapQueryBuilder<T> {
    /**
     * 生成查询map，
     *
     * @param key   查询条件
     * @param value 需要查询的值
     * @return 查询条件-值
     */
    @Deprecated
    LinkedHashMap<T, Object> buildQueryMap(LinkedHashMap<Object, T> key, Map<Object, Object> value);

    /**
     * 生成查询条件
     *
     * @param cb
     * @param root
     * @param condition
     * @param testCondition
     * @return
     */
    Predicate buildMapQueryPredicate(CriteriaBuilder cb, Root root, String path, T condition, Object testCondition);

    /**
     * 生成查询条件
     *
     * @param cb
     * @param root
     * @param condition
     * @return
     */
    Predicate buildMapQueryPredicates(CriteriaBuilder cb, Root root, Map<T, Object> condition);

}
