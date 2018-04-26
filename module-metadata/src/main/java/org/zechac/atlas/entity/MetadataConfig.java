package org.zechac.atlas.entity;


import org.zechac.atlas.common.constant.Constant;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = Constant.PERFIXX_TABLE_SYSTEM + "metadata_config")
@Data
@Cacheable
@Entity
public class MetadataConfig extends BaseEntity {
}
