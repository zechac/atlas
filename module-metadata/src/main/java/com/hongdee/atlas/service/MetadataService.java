package com.hongdee.atlas.service;

import com.hongdee.atlas.common.repo.CustomSpecification;
import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.common.utils.StringUtil;
import com.hongdee.atlas.entity.Metadata;
import com.hongdee.atlas.entity.MetadataGroup;
import com.hongdee.atlas.metadata.jpa.JpaReflector;
import com.hongdee.atlas.metadata.mysql.Reflector;
import com.hongdee.atlas.repo.MetadataGroupRepo;
import com.hongdee.atlas.repo.MetadataRepo;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired(required = false)
    private JpaReflector jpaReflector;

    private void flushMetadatas(List<MetadataGroup> metadataGroups){
        for(MetadataGroup metadataGroup:metadataGroups){
            flushMetadata(metadataGroup);
        }
    }

    private void flushMetadata(MetadataGroup metadataGroup){
        List<Metadata> metadatas=reflector.getMetadatas(metadataGroup);
        for(Metadata metadata:metadatas) {
            if (jpaReflector != null) {
                jpaReflector.fillJpaMetadata(metadata);
            }
        }
        metadataRepo.saveAll(metadatas);
    }

    public void flush(List<MetadataGroup> metadataGroups){
        flushMetadatas(metadataGroups);
        flushPluralAttribute(metadataGroups);
    }

    private void flushPluralAttribute(List<MetadataGroup> metadataGroups){
        for(MetadataGroup metadataGroup:metadataGroups){
            List<Metadata> metadatas=jpaReflector.getPluralAttribute(metadataGroup);
            metadataRepo.saveAll(metadatas);
        }
    }


    public void flush(String groupId){
        MetadataGroup metadataGroup=metadataGroupRepo.findById(groupId).orElse(null);
        Assert.notNull(metadataGroup,"获取元数据组失败id="+groupId);
        flushMetadata(metadataGroup);
    }

    public void delete(String id){
        Metadata metadata=metadataRepo.findById(id).orElse(null);
        Assert.notNull(metadata,"获取数据失败id="+id);
        reflector.deleteMetadata(metadata);
        metadataRepo.delete(metadata);
    }

    public void syncSave(Metadata metadata){
        MetadataGroup metadataGroup=metadataGroupRepo.findById(metadata.getMetadataGroup().getId()).orElse(null);
        metadata.setMetadataGroup(metadataGroup);
        if(StringUtils.isNotBlank(metadata.getId())){
            reflector.mdyMetadata(metadata);
        }else{
            reflector.addMetadata(metadata);
        }
        save(metadata);
    }
}
