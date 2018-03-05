package com.hongdee.atlas.service;

import com.hongdee.atlas.common.repo.CustomSpecification;
import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.entity.Metadata;
import com.hongdee.atlas.entity.MetadataGroup;
import com.hongdee.atlas.metadata.mysql.Reflector;
import com.hongdee.atlas.repo.MetadataGroupRepo;
import com.hongdee.atlas.repo.MetadataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MetadataService extends BaseServiceImpl<Metadata,MetadataRepo>{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MetadataRepo metadataRepo;

    @Autowired
    private MetadataGroupRepo metadataGroupRepo;

    @Autowired
    private Reflector reflector;

    public void flush(String groupId){
        MetadataGroup metadataGroup=metadataGroupRepo.findOne(groupId);
        Assert.notNull(metadataGroup,"获取元数据组失败id="+groupId);
        Map delParam=new HashMap();
        delParam.put("MetadataGroup",metadataGroup);
        delete(delParam);
        List<Metadata> metadatas=reflector.getMetadatas(metadataGroup);
        metadataRepo.save(metadatas);
    }

    public void delete(String id){
        Metadata metadata=metadataRepo.findOne(id);
        Assert.notNull(metadata,"获取数据失败id="+id);
        reflector.deleteMetadata(metadata);
        metadataRepo.delete(metadata);
    }
}
