package com.hongdee.atlas.entity.dynamic;

import com.hongdee.atlas.common.jpa.convert.StringAndListConverter;
import com.hongdee.atlas.common.jpa.entity.BaseEntity;
import com.hongdee.atlas.dynamic.DynamicView;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 动态表单实体
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
