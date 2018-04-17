package com.hongdee.atlas.entity;

import com.alibaba.fastjson.annotation.JSONType;
import com.hongdee.atlas.common.constant.Constant;
import com.hongdee.atlas.common.jpa.entity.BaseEntity;
import com.hongdee.atlas.metadata.mysql.KeyType;
import lombok.Data;

import javax.persistence.*;

/**
 * 元数据类型
 */
@Table(name = Constant.PERFIXX_TABLE_SYSTEM+"metadata")
@Data
@Cacheable
@Entity
@JSONType(ignores = {"metadataGroup"})
public class Metadata extends BaseEntity{

    @Basic
    private String name;

    private String jpaName;

    /**
     * 是否jpa映射
     */
    private boolean standard;

    private String javaType;

    /**
     * jpa类型
     */
    private String jpaType;

    @Basic
    private String description;

    @Basic
    private String type;

    @Basic
    private Boolean nullable;

    @Basic
    private String defaultVal;

    /**
     * 键类型，
     */
    @Enumerated(EnumType.STRING)
    private KeyType keyType;

    @JoinColumn(name = "r_metadata_group")
    @ManyToOne(fetch = FetchType.LAZY)
    private MetadataGroup metadataGroup;
}
