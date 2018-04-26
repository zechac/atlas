package org.zechac.atlas.entity.dynamic;


import org.zechac.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 动态表单项
 *
 * @Author zhaozh
 */
@Table(name = "sys_dynamic_table_item")
@Entity
@Data
public class TableItem extends BaseEntity {
}
