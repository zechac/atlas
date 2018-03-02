package com.hongdee.atlas.common.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.hongdee.atlas.common.jpa.entity.SuperEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by hua on 2017/1/9.
 */
public interface BaseService<K extends SuperEntity> {

    int update(K entity, List<String> field);

    int update(K entity, String... fields);

    int delete(Class<? extends SuperEntity> entity, Map conditions);

    int delete(Map conditions);

    int delete(List ids);

    int update(K entity, Map<String, Object> conditions, String... fields);

    List<K> queryList(Map conditions);

    List<K> queryList(Map conditions, Sort sort);

    List<K> queryList(String jpql, Object... params);

    Iterable findAll();

    Iterable findAll(Sort sort);

    Page findByPage(Pageable pageable);

    Page<K> queryByPage(Map conditions, Pageable pageable);

    K save(K entity);

}
