package org.zechac.atlas.rbac.entity.dynamic;

import org.zechac.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 动态功能按钮
 *
 * @Author zhaozh
 */
@Table(name = "sys_dynamic_button")
@Entity
@Data
public class FormButton extends BaseEntity {

    private String name;

    private String type;

    private String execType;

    private String status;

    private Integer seq;

    private String data;

    private String serviceUrl;

    @ManyToOne
    @JoinColumn
    private DynamicForm dynamicForm;

}
