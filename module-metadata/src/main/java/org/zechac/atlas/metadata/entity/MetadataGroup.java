package org.zechac.atlas.metadata.entity;


import com.alibaba.fastjson.annotation.JSONType;
import org.zechac.atlas.common.constant.Constant;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zechac.atlas.metadata.entity.Metadata;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = Constant.PERFIXX_TABLE_SYSTEM + "metadata_group")
@Data
@Cacheable
@Entity
@NoArgsConstructor
@JSONType(ignores = {"metadatas"})
public class MetadataGroup extends BaseEntity {

    public MetadataGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Basic
    private String name;

    private String jpaName;

    private String javaType;

    private boolean standard;

    /**
     * 数据库名称
     */
    @Basic
    private String module;

    @Basic
    private Date syncDateTime;

    @Basic
    private String description;

    @OneToMany(mappedBy = "metadataGroup", cascade = CascadeType.ALL)
    private List<Metadata> metadatas;
}
