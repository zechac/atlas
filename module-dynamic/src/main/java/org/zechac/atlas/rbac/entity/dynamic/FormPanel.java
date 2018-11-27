package org.zechac.atlas.rbac.entity.dynamic;

import org.zechac.atlas.common.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 动态表单分组
 *
 * @Author zhaozh
 */
@Table(name = "sys_dynamic_form_panel")
@Entity
@Data
public class FormPanel extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn
    private DynamicForm dynamicForm;

    @OneToMany
    private List<FormItem> formItems;
}
