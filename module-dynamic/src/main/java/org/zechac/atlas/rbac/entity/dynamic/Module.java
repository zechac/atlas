package org.zechac.atlas.rbac.entity.dynamic;

import com.alibaba.fastjson.annotation.JSONType;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import org.zechac.atlas.dynamic.DynamicView;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 动态模块实现
 */
@Table(name = "sys_dynamic_module")
@Entity
@Data
@JSONType(ignores = "dynamicForms")
public class Module extends BaseEntity implements DynamicView {

    @Basic
    private String name;

    @Transient
    private List<DynamicView> datas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "r_dynamic_module_form",
            joinColumns = {@JoinColumn(name = "module_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "form_id", referencedColumnName = "id")})
    private List<DynamicForm> dynamicForms;

}
