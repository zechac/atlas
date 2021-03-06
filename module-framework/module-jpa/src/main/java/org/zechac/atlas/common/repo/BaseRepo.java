package org.zechac.atlas.common.repo;

import org.springframework.data.jpa.repository.JpaRepository;
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
public interface BaseRepo<T, I extends Serializable> extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {

    EntityManager getEntityManager();

    int update(T entity, List<String> field);

    int update(T entity, List<String> field, CustomSpecification<I> customSpecification);

    int delete(Class<? extends T> clazz, CustomSpecification<I> customSpecification);

    int delete(CustomSpecification<I> customSpecification);
}
