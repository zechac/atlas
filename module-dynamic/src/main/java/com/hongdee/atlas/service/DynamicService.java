package com.hongdee.atlas.service;


import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.entity.dynamic.DynamicServiceTemplate;
import com.hongdee.atlas.repo.DynamicServiceRepo;
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
public class DynamicService extends BaseServiceImpl<DynamicServiceTemplate,DynamicServiceRepo> {
}
