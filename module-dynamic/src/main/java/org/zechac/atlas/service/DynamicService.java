package org.zechac.atlas.service;


import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.entity.dynamic.DynamicServiceTemplate;
import org.zechac.atlas.repo.DynamicServiceRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @param
 * @param
 * @param
 * @return
 * @throws
 */
@Service
@Transactional
public class DynamicService extends BaseServiceImpl<DynamicServiceTemplate, DynamicServiceRepo> {
}
