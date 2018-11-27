package org.zechac.atlas.rbac.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.rbac.entity.dynamic.DynamicPage;
import org.zechac.atlas.rbac.repo.DynamicPageRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author zhaozh
 */

@Service
@Transactional
public class DynamicPageService extends BaseServiceImpl<DynamicPage, DynamicPageRepo> {
}
