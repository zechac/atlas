package org.zechac.atlas.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.entity.Metadata;
import org.zechac.atlas.entity.MetadataGroup;
import org.zechac.atlas.metadata.jpa.JpaReflector;
import org.zechac.atlas.metadata.mysql.Reflector;
import org.zechac.atlas.repo.MetadataGroupRepo;
import org.zechac.atlas.repo.MetadataRepo;
import org.apache.commons.lang3.StringUtils;
import org.zechac.atlas.metadata.mysql.Reflector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MetadataService extends BaseServiceImpl<Metadata, MetadataRepo> {

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

    private void flushMetadatas(List<MetadataGroup> metadataGroups) {
        for (MetadataGroup metadataGroup : metadataGroups) {
            flushMetadata(metadataGroup);
        }
    }

    private void flushMetadata(MetadataGroup metadataGroup) {
        List<Metadata> metadatas = reflector.getMetadatas(metadataGroup);
        for (Metadata metadata : metadatas) {
            if (jpaReflector != null) {
                jpaReflector.fillJpaMetadata(metadata);
            }
        }
        metadataRepo.saveAll(metadatas);
    }

    public void flush(List<MetadataGroup> metadataGroups) {
        flushMetadatas(metadataGroups);
        flushPluralAttribute(metadataGroups);
    }

    private void flushPluralAttribute(List<MetadataGroup> metadataGroups) {
        for (MetadataGroup metadataGroup : metadataGroups) {
            List<Metadata> metadatas = jpaReflector.getPluralAttribute(metadataGroup);
            metadataRepo.saveAll(metadatas);
        }
    }


    public void flush(String groupId) {
        MetadataGroup metadataGroup = metadataGroupRepo.findById(groupId).orElse(null);
        Assert.notNull(metadataGroup, "获取元数据组失败id=" + groupId);
        flushMetadata(metadataGroup);
    }

    public void delete(String id) {
        Metadata metadata = metadataRepo.findById(id).orElse(null);
        Assert.notNull(metadata, "获取数据失败id=" + id);
        reflector.deleteMetadata(metadata);
        metadataRepo.delete(metadata);
    }

    public void syncSave(Metadata metadata) {
        MetadataGroup metadataGroup = metadataGroupRepo.findById(metadata.getMetadataGroup().getId()).orElse(null);
        metadata.setMetadataGroup(metadataGroup);
        if (StringUtils.isNotBlank(metadata.getId())) {
            reflector.mdyMetadata(metadata);
        } else {
            reflector.addMetadata(metadata);
        }
        save(metadata);
    }
}
