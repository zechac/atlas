package org.zechac.atlas.rbac.service;


import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.rbac.entity.dynamic.DynamicServiceTemplate;
import org.zechac.atlas.rbac.repo.DynamicServiceRepo;
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
