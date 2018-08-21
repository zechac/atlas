package org.zechac.atlas.common.repo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.util.Optionals;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.*;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * Created by zhaozh on 2016-5-17.
 */
@Slf4j
@Transactional
public class BaseRepoImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepo<T, ID> {

    private final EntityManager entityManager;

    private JpaEntityInformation jpaEntityInformation;

    public JpaEntityInformation getJpaEntityInformation() {
        return jpaEntityInformation;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public BaseRepoImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        // Keep the EntityManager around to used from the newly introduced methods.
        this.entityManager = entityManager;
        this.jpaEntityInformation = entityInformation;
    }

    public int update(T entity, List<String> field) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate criteriaUpdate = criteriaBuilder.createCriteriaUpdate(entity.getClass());
        criteriaUpdate.from(entity.getClass());
        Root root = criteriaUpdate.getRoot();
        try {
            for (String s : field) {
                criteriaUpdate.set(root.get(s), PropertyUtils.getProperty(entity, s));
            }
            criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), PropertyUtils.getProperty(entity, "id")));
        } catch (Exception e) {
            log.error(String.format("update error:") + e.getMessage());
        }
        int ret = entityManager.createQuery(criteriaUpdate).executeUpdate();

        return ret;
    }

    public int update(T entity, List<String> field, CustomSpecification<ID> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate criteriaUpdate = criteriaBuilder.createCriteriaUpdate(entity.getClass());
        criteriaUpdate.from(entity.getClass());
        Root root = criteriaUpdate.getRoot();
        for (String s : field) {
            try {
                criteriaUpdate.set(root.get(s), PropertyUtils.getProperty(entity, s));
            } catch (Exception e) {
                log.error(String.format("update field[%s]:", s) + e.getMessage());
            }
        }
        criteriaUpdate.where(specification.toPredicate(root, criteriaBuilder));
        int ret = entityManager.createQuery(criteriaUpdate).executeUpdate();

        return ret;
    }

    /**
     * 条件删除
     *
     * @param clazz
     * @param customSpecification 自定义查询条件
     * @return
     */
    public int delete(Class<? extends T> clazz, CustomSpecification<ID> customSpecification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete criteriaDelete = criteriaBuilder.createCriteriaDelete(clazz);
        criteriaDelete.from(clazz);
        criteriaDelete.where(customSpecification.toPredicate(criteriaDelete.getRoot(), criteriaBuilder));
        int ret = entityManager.createQuery(criteriaDelete).executeUpdate();
        return ret;
    }

    @Override
    public int delete(CustomSpecification<ID> customSpecification) {
        return delete(getDomainClass(), customSpecification);
    }

    /**
     * Applies the given {@link Specification} to the given {@link CriteriaQuery}.
     *
     * @param spec can be {@literal null}.
     * @param domainClass must not be {@literal null}.
     * @param query must not be {@literal null}.
     * @return
     */
    private <S, U extends T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass,
                                                                  CriteriaQuery<S> query) {

        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(query, "CriteriaQuery must not be null!");

        Root<U> root = query.from(domainClass);

        if (spec == null) {
            return root;
        }

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

    /**
     * Creates a {@link TypedQuery} for the given {@link Specification} and {@link Sort}.
     *
     * @param spec can be {@literal null}.
     * @param domainClass must not be {@literal null}.
     * @param sort must not be {@literal null}.
     * @return
     */
    @Override
    protected <S extends T> TypedQuery<S> getQuery(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<S> query = builder.createQuery(domainClass);

        Root<S> root = applySpecificationToCriteria(spec, domainClass, query);
        if(query.getSelection()==null) {
            query.select(root);
        }

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }

        return applyRepositoryMethodMetadata(entityManager.createQuery(query));
    }

    private <S> TypedQuery<S> applyRepositoryMethodMetadata(TypedQuery<S> query) {

        if (getRepositoryMethodMetadata() == null) {
            return query;
        }

        LockModeType type = getRepositoryMethodMetadata().getLockModeType();
        TypedQuery<S> toReturn = type == null ? query : query.setLockMode(type);

        applyQueryHints(toReturn);

        return toReturn;
    }

    private void applyQueryHints(Query query) {
        if(getRepositoryMethodMetadata() == null){
            return;
        }

        for (Map.Entry<String, Object> hint : asMap().entrySet()) {
            query.setHint(hint.getKey(), hint.getValue());
        }
    }

    public Map<String, Object> asMap() {

        Map<String, Object> hints = new HashMap<>();

        hints.putAll(getRepositoryMethodMetadata().getQueryHints());
        hints.putAll(getFetchGraphs());

        return hints;
    }

    private Map<String, Object> getFetchGraphs() {
        Optional<EntityManager> optional=Optional.of(entityManager);
        return org.springframework.data.util.Optionals
                .mapIfAllPresent(optional, getRepositoryMethodMetadata().getEntityGraph(),
                        (em, graph) -> Jpa21Utils.tryGetFetchGraphHints(em, getEntityGraph(graph), jpaEntityInformation.getJavaType()))
                .orElse(Collections.emptyMap());
    }

    private JpaEntityGraph getEntityGraph(EntityGraph entityGraph) {
        String fallbackName = jpaEntityInformation.getEntityName() + "." + getRepositoryMethodMetadata().getMethod().getName();
        return new JpaEntityGraph(entityGraph, fallbackName);
    }

}
