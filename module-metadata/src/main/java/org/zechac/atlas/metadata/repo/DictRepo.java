package org.zechac.atlas.metadata.repo;

import org.zechac.atlas.common.repo.BaseRepo;
import org.zechac.atlas.metadata.entity.Dict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by My on 2018-04-01.
 */
public interface DictRepo extends BaseRepo<Dict, String> {
    @Query(value = "select o from  Dict o where o.dPCode = :pid and o.dType='0'")
    List runSql(@Param("pid") String pid);
}
