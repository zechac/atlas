package org.zechac.atlas.rbac.entity.dynamic;

import org.zechac.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 动态表单项
 *
 * @Author zhaozh
 */
@Table(name = "sys_dynamic_form_item")
@Entity
@Data
public class FormItem extends BaseEntity {

    private String tableName;

    private String propName;

    private String description;

    /**
     * 类型
     */
    private String type;

    /**
     * 值类型
     */
    private String valueType;

    /**
     * 数值长度
     */
    private Integer length;

    /**
     * 状态
     */
    private String status;

    @ManyToOne
    @JoinColumn
    private FormPanel formPanel;

}
