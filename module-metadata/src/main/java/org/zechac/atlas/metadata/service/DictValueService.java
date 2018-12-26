package org.zechac.atlas.metadata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.metadata.entity.DictValue;
import org.zechac.atlas.metadata.repo.DictValueRepo;

import javax.transaction.Transactional;

@Transactional
@Service
public class DictValueService extends BaseServiceImpl<DictValue, DictValueRepo> {
    @Autowired
    private DictValueRepo dictValueRepo;
    public DictValue findTopByDictKeyId(String id) {
        return dictValueRepo.findTopByDictKeyId(id);
    }
}
