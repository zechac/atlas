package com.hongdee.atlas.entity.dynamic;

import com.alibaba.fastjson.JSONArray;
import com.hongdee.atlas.common.jpa.convert.StringAndJSONArrayConverter;
import com.hongdee.atlas.common.jpa.convert.StringAndListConverter;
import com.hongdee.atlas.common.jpa.entity.BaseEntity;
import com.hongdee.atlas.dynamic.InvokeType;
import com.hongdee.atlas.dynamic.ServiceType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 *
 */
@Table(name = "sys_dynamic_service_template")
@Entity
@Data
public class DynamicServiceTemplate extends BaseEntity {

    private String name;

    private String description;

    /**
     * 服务调用模板
     * [{
     *     kind:"sql-select",
     *     sql:"select * from table where a=#a"
     * },{
     *     kind:""
     * }]
     */
    @Convert(converter = StringAndJSONArrayConverter.class)
    @Column(name = "service_template",columnDefinition = "text")
    private JSONArray serviceTemplate;

}
