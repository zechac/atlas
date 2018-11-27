package org.zechac.atlas.rbac.entity.dynamic;

import org.zechac.atlas.common.jpa.convert.StringAndListConverter;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import org.zechac.atlas.dynamic.DynamicView;
import lombok.Data;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 动态表单实体
 *
 * @Author zhaozh
 */
@Table(name = "sys_dynamic_form")
@Entity
@Data
public class DynamicForm extends BaseEntity implements DynamicView {

    private String name;

    /**
     * 主表
     */
    private String masterTable;

    @Convert(converter = StringAndListConverter.class)
    private List<String> salveTables;

    @OneToMany
    private List<FormPanel> formPanels;

    @OneToMany
    private List<FormButton> formButton;
}
