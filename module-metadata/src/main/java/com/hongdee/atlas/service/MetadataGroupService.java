package com.hongdee.atlas.service;

import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.common.utils.StringUtil;
import com.hongdee.atlas.entity.Metadata;
import com.hongdee.atlas.entity.MetadataGroup;
import com.hongdee.atlas.metadata.jpa.JpaReflector;
import com.hongdee.atlas.metadata.mysql.KeyType;
import com.hongdee.atlas.metadata.mysql.Reflector;
import com.hongdee.atlas.repo.MetadataGroupRepo;
import com.hongdee.atlas.repo.MetadataRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MetadataGroupService extends BaseServiceImpl<MetadataGroup,MetadataGroupRepo>{

    @Autowired
    private MetadataGroupRepo metadataGroupRepo;

    @Autowired
    private MetadataRepo metadataRepo;

    @Autowired
    private MetadataService metadataService;

    @Autowired
    private Reflector reflector;

    @Autowired(required = false)
    private JpaReflector jpaReflector;

    /**
     * 刷新table元数据
     */
    private List<MetadataGroup> flushMetadataGroup(){
        List<MetadataGroup> metadataGroups=reflector.getMetadataGroups(null);
        if(jpaReflector!=null){
            for(MetadataGroup metadataGroup:metadataGroups){
                jpaReflector.fillJpaMetadataGroup(metadataGroup);
            }
        }
        metadataGroupRepo.saveAll(metadataGroups);
        return metadataGroups;
    }

    /**
     * 刷新元数据组，即通过DDL重新生成数据表的信息
     * 注意：刷新数据会删除之前保存的信息
     */
    public void flush(){
        metadataRepo.deleteAll();
        metadataGroupRepo.deleteAll();
        List<MetadataGroup> metadataGroups=flushMetadataGroup();
        metadataService.flush(metadataGroups);
    }

    /**
     * 刷新元数据组，即通过DDL重新生成数据表的信息
     * 注意：刷新数据会删除之前保存的信息
     */
    public void flushGroup(){
        flushMetadataGroup();
    }

    public void delete(String id){
        MetadataGroup metadataGroup=findById(id);
        reflector.deleteMetadataGroup(metadataGroup);
        deleteById(id);
    }


    public void syncSave(MetadataGroup metadataGroup){
        if(StringUtils.isNotBlank(metadataGroup.getId())){//修改
            MetadataGroup metadataGroup1=findById(metadataGroup.getId());
            if(!metadataGroup1.getName().equals(metadataGroup.getName())){
                metadataGroup1.setName(metadataGroup1.getName());
                reflector.mdyMetadataGroup(metadataGroup1,metadataGroup);
            }
            metadataGroupRepo.save(metadataGroup);
        }else{//新增
            Metadata metadata=new Metadata();
            metadata.setKeyType(KeyType.PRI);
            metadata.setName("id");
            metadata.setStandard(false);
            metadata.setNullable(false);
            metadata.setType("varchar(36)");
            metadata.setDescription("主键");
            metadata.setMetadataGroup(metadataGroup);
            List<Metadata> metadataList=new ArrayList<>();
            metadataList.add(metadata);
            metadataGroup.setMetadatas(metadataList);
            reflector.addMetadataGroup(metadataGroup);
            metadataGroupRepo.save(metadataGroup);
        }
    }
}
