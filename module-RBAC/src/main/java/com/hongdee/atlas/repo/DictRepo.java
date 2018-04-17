package com.hongdee.atlas.repo;

import com.hongdee.atlas.common.repo.BaseRepo;
import com.hongdee.atlas.entity.rbac.Dict;
import com.hongdee.atlas.entity.rbac.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by My on 2018-04-01.
 */
public interface DictRepo extends BaseRepo<Dict,String>{
    @Query(value = "select o from  Dict o where o.dPCode = :pid and o.dType='0'")
    List runSql(@Param("pid") String pid);
}
