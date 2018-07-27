package org.zechac.atlas.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.entity.dynamic.DynamicPage;
import org.zechac.atlas.repo.DynamicPageRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author zhaozh
 */

@Service
@Transactional
public class DynamicPageService extends BaseServiceImpl<DynamicPage, DynamicPageRepo> {
}
