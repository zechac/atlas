package com.hongdee.atlas.service;

import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.entity.dynamic.DynamicPage;
import com.hongdee.atlas.repo.DynamicPageRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author zhaozh
 */

@Service
@Transactional
public class DynamicPageService extends BaseServiceImpl<DynamicPage,DynamicPageRepo> {
}
