package org.zechac.atlas.metadata.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zechac.atlas.common.utils.StringUtil;
import org.zechac.atlas.metadata.entity.DictKey;
import org.zechac.atlas.metadata.entity.DictValue;
import org.zechac.atlas.metadata.repo.DictKeyRepo;
import org.zechac.atlas.metadata.repo.DictValueRepo;

import javax.transaction.Transactional;

@Transactional
@Service
public class DictService {
    @Autowired
    private DictValueRepo dictValueRepo;
    @Autowired
    private DictKeyRepo dictKeyRepo;

    /**
     * 1.key已经存在时，删除key下所有的value，重新保存，
     * 2.key不存在时，保存全部
     * @param dict
     */
    public void save(DictKey dict) {
        if(StringUtils.isNotBlank(dict.getId())){
            dictValueRepo.deleteByDictKey(dict);
        }
        dictKeyRepo.save(dict);
        if(dict.getDictValues()!=null) {
            for (DictValue dv : dict.getDictValues()) {
                dv.setDictKey(dict);
                dictValueRepo.save(dv);
            }
        }
    }
}
