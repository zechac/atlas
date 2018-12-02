package org.zechac.atlas.rbac.entity.dynamic;


import com.alibaba.fastjson.JSONObject;
import org.zechac.atlas.common.jpa.convert.StringAndJSONObjectConvert;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import org.zechac.atlas.dynamic.DynamicView;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TODO:一个json schema 动态生成的页面
 */
@Table(name = "sys_dynamic_page")
@Entity
@Data
public class DynamicPage extends BaseEntity implements DynamicView {

    @Convert(converter = StringAndJSONObjectConvert.class)
    private JSONObject editorData;

    @Convert(converter = StringAndJSONObjectConvert.class)
    @Column(name = "view_data", columnDefinition = "text")
    private JSONObject viewData;

    private String name;

    private String type;

    private String description;

}
