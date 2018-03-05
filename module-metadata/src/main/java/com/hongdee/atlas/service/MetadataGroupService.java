package com.hongdee.atlas.service;

import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.entity.MetadataGroup;
import com.hongdee.atlas.metadata.mysql.Reflector;
import com.hongdee.atlas.repo.MetadataGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MetadataGroupService extends BaseServiceImpl<MetadataGroup,MetadataGroupRepo>{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MetadataGroupRepo metadataGroupRepo;

    @Autowired
    private Reflector reflector;

    /**
     * 刷新元数据组，即通过DDL重新生成数据表的信息
     * 注意：刷新数据会删除之前保存的信息
     */
    public void flush(){
        List<MetadataGroup> metadataGroups=reflector.getMetadataGroups(null);
        metadataGroupRepo.save(metadataGroups);
    }
}
