package com.hongdee.atlas.common.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * Created by zhaozh on 2016-5-17.
 */
@NoRepositoryBean
public interface BaseRepo<T,I extends Serializable> extends PagingAndSortingRepository<T,I>,JpaSpecificationExecutor<T> {

    EntityManager getEntityManager();

    int update(T entity, List<String> field);
    int update(T entity, List<String> field, CustomSpecification<I> customSpecification);
    int delete(Class<? extends T> clazz, CustomSpecification<I> customSpecification);
    int delete(CustomSpecification<I> customSpecification);

    /**
     * 自定义返回值的查询
     * @param spec
     * @param pageable
     * @param select
     * @return
     */
    Page<T> queryByPage(Specification<T> spec, Pageable pageable,String... select);
}
