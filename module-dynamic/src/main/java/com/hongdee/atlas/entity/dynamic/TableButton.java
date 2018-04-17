package com.hongdee.atlas.entity.dynamic;


import com.hongdee.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 列表按钮
 */
@Table(name = "sys_dynamic_table_button")
@Entity
@Data
public class TableButton extends BaseEntity{
}
