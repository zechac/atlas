package com.hongdee.atlas.common.repo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.QueryHint;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * Created by zhaozh on 2016-5-17.
 */
@Slf4j
@Transactional
public class BaseRepoImpl<T,ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepo<T, ID> {

    private final EntityManager entityManager;

    private JpaEntityInformation jpaEntityInformation;

    public JpaEntityInformation getJpaEntityInformation() {
        return jpaEntityInformation;
    }

    public EntityManager getEntityManager(){
        return this.entityManager;
    }

    public BaseRepoImpl(JpaEntityInformation entityInformation,EntityManager entityManager) {
        super(entityInformation, entityManager);

        // Keep the EntityManager around to used from the newly introduced methods.
        this.entityManager = entityManager;
        this.jpaEntityInformation=entityInformation;
    }

    public int update(T entity,List<String> field){
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaUpdate criteriaUpdate= criteriaBuilder.createCriteriaUpdate(entity.getClass());
        criteriaUpdate.from(entity.getClass());
        Root root=criteriaUpdate.getRoot();
        try{
            for (String s: field){
                criteriaUpdate.set(root.get(s), PropertyUtils.getProperty(entity,s));
            }
            criteriaUpdate.where(criteriaBuilder.equal(root.get("id"),PropertyUtils.getProperty(entity,"id")));
        }catch (Exception e){
            log.error(String.format("update error:")+e.getMessage());
        }
        int ret= entityManager.createQuery(criteriaUpdate).executeUpdate();

        return ret;
    }

    public int update(T entity, List<String> field, CustomSpecification<ID> specification){
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaUpdate criteriaUpdate= criteriaBuilder.createCriteriaUpdate(entity.getClass());
        criteriaUpdate.from(entity.getClass());
        Root root=criteriaUpdate.getRoot();
        for (String s: field){
            try{
                criteriaUpdate.set(root.get(s), PropertyUtils.getProperty(entity,s));
            }catch (Exception e){
                log.error(String.format("update field[%s]:",s)+e.getMessage());
            }
        }
       criteriaUpdate.where(specification.toPredicate(root,criteriaBuilder));
        int ret= entityManager.createQuery(criteriaUpdate).executeUpdate();

        return ret;
    }

    /**
     * 条件删除
     * @param clazz
     * @param customSpecification 自定义查询条件
     * @return
     */
    public int delete(Class<? extends T> clazz,CustomSpecification<ID> customSpecification){
        CriteriaBuilder criteriaBuilder= entityManager.getCriteriaBuilder();
        CriteriaDelete criteriaDelete= criteriaBuilder.createCriteriaDelete(clazz);
        criteriaDelete.from(clazz);
        criteriaDelete.where(customSpecification.toPredicate(criteriaDelete.getRoot(),criteriaBuilder));
        int ret= entityManager.createQuery(criteriaDelete).executeUpdate();
        return ret;
    }

    @Override
    public int delete(CustomSpecification<ID> customSpecification) {
        return delete(getDomainClass(),customSpecification);
    }

}
