package com.hongdee.atlas.service;

import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.entity.Metadata;
import com.hongdee.atlas.repo.MetadataRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MetadataService extends BaseServiceImpl<Metadata,MetadataRepo>{

}
