package com.hongdee.atlas.entity.dynamic;


import com.hongdee.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 动态表单项
 * @Author zhaozh
 */
@Table(name = "sys_dynamic_table_item")
@Entity
@Data
public class TableItem extends BaseEntity{
}
