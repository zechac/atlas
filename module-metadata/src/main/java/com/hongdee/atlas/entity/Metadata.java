package com.hongdee.atlas.entity;

import com.hongdee.atlas.common.constant.Constant;
import com.hongdee.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * 元数据类型
 */
@Table(name = Constant.PERFIXX_TABLE_SYSTEM+"metadata")
@Data
@Cacheable
@Entity
public class Metadata extends BaseEntity{

    @Basic
    private String name;

    @Basic
    private String description;

    @Basic
    private String type;

    @JoinColumn(name = "r_metadata_group")
    @ManyToOne(fetch = FetchType.LAZY)
    private MetadataGroup metadataGroup;
}
