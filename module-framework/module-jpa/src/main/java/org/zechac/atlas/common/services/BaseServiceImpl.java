package org.zechac.atlas.common.services;

import org.zechac.atlas.common.jpa.entity.AuditingEntity;
import org.zechac.atlas.common.jpa.entity.SuperEntity;
import org.zechac.atlas.common.jpa.query.MapQueryBuilder;
import org.zechac.atlas.common.repo.BaseRepo;
import org.zechac.atlas.common.repo.CustomSpecification;
import org.apache.commons.lang3.ArrayUtils;
import org.zechac.atlas.common.repo.BaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Administrator on 2016-5-18.
 */
@Transactional
public abstract class BaseServiceImpl<K extends SuperEntity, T extends BaseRepo> implements BaseService<K> {

    protected T entityDao;

    protected MapQueryBuilder mapQueryBuilder;

    @Autowired
    public void setMapQueryBuilder(MapQueryBuilder mapQueryBuilder) {
        this.mapQueryBuilder = mapQueryBuilder;
    }

    @Autowired
    public void setEntityDao(T entityDao) {
        this.entityDao = entityDao;
    }

    public T getEntityDao() {
        return this.entityDao;
    }

    @Autowired(required = false)
    private AuditorAware auditorAware;

    public K save(K entity) {
        if (onBeforeSave != null) {
            this.onBeforeSave.beforeSave(entity);
        }
        return (K) entityDao.save(entity);
    }

    private OnBeforeSave<K> onBeforeSave;
    private OnBeforeUpdate<K> onBeforeUpdate;

    public void setOnBeforeSave(OnBeforeSave<K> onBeforeSave) {
        this.onBeforeSave = onBeforeSave;
    }

    public void setOnBeforeUpdate(OnBeforeUpdate<K> onBeforeUpdate) {
        this.onBeforeUpdate = onBeforeUpdate;
    }

    private void setAudition(K entity, List<String> field) {
        if (auditorAware != null && AuditingEntity.class.isAssignableFrom(entity.getClass())) {
            Object user = auditorAware.getCurrentAuditor();
            AuditingEntity baseEntity = (AuditingEntity) entity;
            if (user != null) {
                baseEntity.setLastUpdateUserId(user.toString());
            }
            baseEntity.setLastUpdateDatetime(new Date());
            field = Arrays.asList(ArrayUtils.addAll(field.toArray(new String[]{}), "lastUpdateDatetime", "lastUpdateUserId"));
        }
    }

    /**
     * 更新选定字段
     *
     * @param entity
     * @param field
     * @return
     */
    public int update(K entity, List<String> field) {
        setAudition(entity, field);
        if (onBeforeUpdate != null) {
            this.onBeforeUpdate.beforeUpdate(entity);
        }
        return entityDao.update(entity, field);
    }

    public int update(K entity, String... fields) {
        List<String> fieldList = new ArrayList<>();
        if (fields.length > 0) {
            for (int i = 0; i < fields.length; i++) {
                fieldList.add(fields[i]);
            }
        }
        return update(entity, fieldList);
    }

    public int delete(Class<? extends SuperEntity> entity, Map conditions) {
        return entityDao.delete(entity, buildCustonSpecification(conditions));
    }

    public int delete(final List ids) {
        CustomSpecification<T> customSpecification = new CustomSpecification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaBuilder criteriaBuilder) {
                CriteriaBuilder.In in = criteriaBuilder.in(root.get("id"));
                for (Object id : ids) {
                    in.value(id);
                }
                return in;
            }
        };
        return entityDao.delete(customSpecification);
    }

    public int delete(Map conditions) {
        return entityDao.delete(buildCustonSpecification(conditions));
    }

    public void deleteEntity(K entity) {
        entityDao.delete(entity);
    }

    public void deleteById(Serializable id) {
        entityDao.deleteById(id);
    }

    public int update(K entity, Map<String, Object> conditions, String... fields) {
        List<String> fieldList = new ArrayList<>();
        if (fields.length > 0) {
            for (int i = 0; i < fields.length; i++) {
                fieldList.add(fields[i]);
            }
        }
        setAudition(entity, fieldList);
        if (onBeforeUpdate != null) {
            this.onBeforeUpdate.beforeUpdate(entity);
        }
        return entityDao.update(entity, fieldList, buildCustonSpecification(conditions));
    }

    public List<K> queryList(Map conditions) {
        Specification specifications = buildSpecification(conditions);
        return entityDao.findAll(specifications);
    }

    public List<K> queryList(Map conditions, Sort sort) {
        Specification specifications = buildSpecification(conditions);
        return entityDao.findAll(specifications, sort);
    }

    public List<K> queryList(String jpql, Object... params) {
        EntityManager entityManager = entityDao.getEntityManager();
        Query query = entityManager.createQuery(jpql);
        if (params != null)
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        return query.getResultList();
    }

    public long count(Map m) {
        Specification specification = buildSpecification(m);
        return entityDao.count(specification);
    }

    public List<Object[]> queryListNative(String sql) {
        EntityManager entityManager = entityDao.getEntityManager();
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    public K findById(Serializable id) {
        return (K) entityDao.findById(id).orElse(null);
    }

    public List<K> findAll() {
        return entityDao.findAll();
    }

    public List<K> findAll(Sort sort) {
        return entityDao.findAll(sort);
    }

    public Page findByPage(Pageable pageable) {
        return entityDao.findAll(pageable);
    }

    public Page<K> queryByPage(Map conditions, Pageable pageable) {
        Specification specifications = buildSpecification(conditions);
        return entityDao.findAll(specifications, pageable);
    }

    public Page<K> queryByPage(Map conditions, Pageable pageable, String... select) {
        Specification specifications = buildSpecification(conditions, select);
        return entityDao.findAll(specifications, pageable);
    }

    public K queryOne(Map conditions) {
        Specification specifications = buildSpecification(conditions);
        return (K) entityDao.findOne(specifications).orElse(null);
    }

    protected Specification buildSpecification(final Map<String, Object> conditions) {
        final Specification specification = new Specification() {
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return mapQueryBuilder.buildMapQueryPredicates(cb, root, conditions);
            }
        };
        return specification;
    }

    /**
     * 返回 指定的select 字段
     *
     * @param criteriaQuery
     * @param root
     * @param sel
     */
    protected void buildSelect(CriteriaQuery criteriaQuery, Root root, String... sel) {
        List<Selection> selections = new ArrayList<>();
        for (String s : sel) {
            selections.add(root.get(s));
        }
        criteriaQuery.multiselect(selections);
    }

    protected Specification buildSpecification(final Map<String, Object> conditions, String... select) {
        final Specification specification = new Specification() {
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                if (select != null && select.length > 0) {
                    buildSelect(query, root, select);
                } else {
                    query.select(root);
                }
                return mapQueryBuilder.buildMapQueryPredicates(cb, root, conditions);
            }
        };
        return specification;
    }

    protected CustomSpecification buildCustonSpecification(final Map<String, Object> conditions) {
        final CustomSpecification specification = new CustomSpecification() {
            public Predicate toPredicate(Root root, CriteriaBuilder cb) {
                return mapQueryBuilder.buildMapQueryPredicates(cb, root, conditions);
            }
        };
        return specification;
    }

}
