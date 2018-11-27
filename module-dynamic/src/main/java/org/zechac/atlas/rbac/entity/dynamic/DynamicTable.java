package org.zechac.atlas.rbac.entity.dynamic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.zechac.atlas.common.jpa.convert.StringAndJSONArrayConverter;
import org.zechac.atlas.common.jpa.convert.StringAndJSONObjectConvert;
import org.zechac.atlas.common.jpa.entity.BaseEntity;
import org.zechac.atlas.dynamic.DynamicView;
import lombok.Data;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "sys_dynamic_table")
@Entity
@Data
public class DynamicTable extends BaseEntity implements DynamicView {

    @Convert(converter = StringAndJSONObjectConvert.class)
    private JSONObject css;

    @Convert(converter = StringAndJSONObjectConvert.class)
    private JSONObject style;

    private String labelPosition = "left";

    private String labelWidth = "80px";

    private String size;

    private String name;

    /**
     * 列表项
     */
    @Convert(converter = StringAndJSONArrayConverter.class)
    private JSONArray items;


    /**
     * 查询条件
     */
    @Convert(converter = StringAndJSONObjectConvert.class)
    private JSONObject filter;
}
