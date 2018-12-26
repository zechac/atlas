package org.zechac.atlas.metadata.repo;

import org.zechac.atlas.common.repo.BaseRepo;
import org.zechac.atlas.metadata.entity.DictKey;
import org.zechac.atlas.metadata.entity.DictValue;

public interface DictValueRepo extends BaseRepo<DictValue,String> {
    void deleteByDictKey(DictKey dict);

    DictValue findTopByDictKeyId(String id);
}
