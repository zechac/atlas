package com.hongdee.atlas.common.repo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by zhaozh on 2016/10/19/019.
 * 用于更新，删除的查询条件
 */
public interface CustomSpecification<T> {
    Predicate toPredicate(Root<T> root, CriteriaBuilder criteriaBuilder);
}
