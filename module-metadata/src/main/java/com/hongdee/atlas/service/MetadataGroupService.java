package com.hongdee.atlas.service;

import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.entity.MetadataGroup;
import com.hongdee.atlas.repo.MetadataGroupRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MetadataGroupService extends BaseServiceImpl<MetadataGroup,MetadataGroupRepo>{
}
