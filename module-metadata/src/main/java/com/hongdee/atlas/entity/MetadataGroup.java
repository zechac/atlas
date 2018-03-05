package com.hongdee.atlas.entity;


import com.hongdee.atlas.common.constant.Constant;
import com.hongdee.atlas.common.jpa.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = Constant.PERFIXX_TABLE_SYSTEM+"metadata_group")
@Data
@Cacheable
@Entity
@NoArgsConstructor
public class MetadataGroup extends BaseEntity{

    public MetadataGroup(String id,String name){
        this.id=id;
        this.name=name;
    }

    @Basic
    private String name;

    /**
     * 数据库名称
     */
    @Basic
    private String module;

    @Basic
    private Date syncDateTime;

    @Basic
    private String description;

    @OneToMany(mappedBy = "metadataGroup")
    private List<Metadata> metadatas;
}
