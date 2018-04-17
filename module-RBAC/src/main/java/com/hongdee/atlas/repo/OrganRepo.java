package com.hongdee.atlas.repo;

import com.hongdee.atlas.common.repo.BaseRepo;
import com.hongdee.atlas.entity.rbac.Organ;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
*@author作者：王恒腾
*类名：
*功能：部门接口层
*时间：2018-03-21/15:51
*
*/
public interface OrganRepo extends BaseRepo<Organ,String>{

    @Query(value = "select o from  Organ o where o.pId = :pid")
    List runSql(@Param("pid") String pid);

}
