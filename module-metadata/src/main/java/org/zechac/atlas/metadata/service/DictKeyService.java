package org.zechac.atlas.metadata.service;

import org.springframework.stereotype.Service;
import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.metadata.entity.DictKey;
import org.zechac.atlas.metadata.repo.DictKeyRepo;

import javax.transaction.Transactional;

@Transactional
@Service
public class DictKeyService extends BaseServiceImpl<DictKey, DictKeyRepo> {
}
